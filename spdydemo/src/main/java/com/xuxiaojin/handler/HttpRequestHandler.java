package com.xuxiaojin.handler;


import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }
        FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),HttpResponseStatus.OK);
        response.content().writeBytes(getContent().getBytes(StandardCharsets.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
        boolean keepAlive=HttpUtil.isKeepAlive(request);
        if(keepAlive){
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }
        ChannelFuture future=ctx.writeAndFlush(response);
        if(!keepAlive){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }


    protected String getContent(){
        return "This content is transmitted via HTTP\r\n";
    }
    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE
        );
        ctx.writeAndFlush(response);
    }
}
