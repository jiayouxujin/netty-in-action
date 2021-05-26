package com.xuxiaojin.handler;

public class SpdyRequestHandler extends HttpRequestHandler{

    @Override
    protected String getContent() {
        return "This content is transmitted cia SPDY\r\n";
    }
}
