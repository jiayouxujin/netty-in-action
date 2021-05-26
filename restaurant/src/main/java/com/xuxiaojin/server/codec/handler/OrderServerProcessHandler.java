package com.xuxiaojin.server.codec.handler;

import com.xuxiaojin.common.Operation;
import com.xuxiaojin.common.OperationResult;
import com.xuxiaojin.common.RequestMessage;
import com.xuxiaojin.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation=requestMessage.getMessageBody();
        OperationResult result=operation.execute();

        ResponseMessage responseMessage=new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(result);

        ctx.writeAndFlush(responseMessage);
    }
}
