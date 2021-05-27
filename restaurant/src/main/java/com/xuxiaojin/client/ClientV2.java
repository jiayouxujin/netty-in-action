package com.xuxiaojin.client;

import com.xuxiaojin.client.codec.*;
import com.xuxiaojin.client.codec.dispatcher.OperationResultFuture;
import com.xuxiaojin.client.codec.dispatcher.RequestPendingCenter;
import com.xuxiaojin.client.codec.dispatcher.ResponseDispatcherHandler;
import com.xuxiaojin.common.OperationResult;
import com.xuxiaojin.common.RequestMessage;
import com.xuxiaojin.common.order.OrderOperation;
import com.xuxiaojin.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ExecutionException;

public class ClientV2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);

        bootstrap.group(new NioEventLoopGroup());

        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());

                pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));
                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090);

        channelFuture.sync();
        long streamId = IdUtil.nextId();
        OrderOperation operation = new OrderOperation(1001, "tudo");
        RequestMessage requestMessage = new RequestMessage(streamId, operation);
        OperationResultFuture operationResultFuture = new OperationResultFuture();
        requestPendingCenter.add(streamId, operationResultFuture);
        channelFuture.channel().writeAndFlush(requestMessage);

        OperationResult result = operationResultFuture.get();
        System.out.println(result);
        channelFuture.channel().closeFuture().get();
    }
}
