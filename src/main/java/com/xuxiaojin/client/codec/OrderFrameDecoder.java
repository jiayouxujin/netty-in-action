package com.xuxiaojin.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(Integer.MAX_VALUE,0,2,0,2);
    }
}
