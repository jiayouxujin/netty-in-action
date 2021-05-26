package com.xuxiaojin.client.codec;

import com.xuxiaojin.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf byteBuf=ctx.alloc().buffer();
        requestMessage.encode(byteBuf);

        out.add(byteBuf);
    }
}
