package com.xuxiaojin.chooser;

import com.xuxiaojin.handler.HttpRequestHandler;
import com.xuxiaojin.handler.SpdyRequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.ApplicationProtocolNegotiationHandler;

public class DefaultSpdyOrHttpChooser extends ApplicationProtocolNegotiationHandler {
    /**
     * Creates a new instance with the specified fallback protocol name.
     */
    public DefaultSpdyOrHttpChooser() {
        super(ApplicationProtocolNames.HTTP_1_1);
    }

    @Override
    protected void configurePipeline(ChannelHandlerContext ctx, String protocol) throws Exception {
        if (ApplicationProtocolNames.SPDY_2.equals(protocol)) {
            configureSpdy(ctx);
        } else if (ApplicationProtocolNames.SPDY_3.equals(protocol)) {
            configureSpdy(ctx);
        } else if (ApplicationProtocolNames.HTTP_1_1.equals(protocol)) {
            configureHttp(ctx);
        } else {
            throw new IllegalStateException("unknown protocol: " + protocol);
        }
    }

    private void configureHttp(ChannelHandlerContext ctx) {
        ctx.pipeline().addLast(new HttpRequestHandler());
    }

    private void configureSpdy(ChannelHandlerContext ctx) {
        ctx.pipeline().addLast(new SpdyRequestHandler());
    }

}
