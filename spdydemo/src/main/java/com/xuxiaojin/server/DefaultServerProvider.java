package com.xuxiaojin.server;

import org.eclipse.jetty.npn.NextProtoNego;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultServerProvider implements NextProtoNego.ServerProvider {
    //define server-side supported protocols
    private static final List<String> PROTOCOLS = Collections.unmodifiableList(
            Arrays.asList("spdy/2", "spdy/3", "http/1.1"));
    private String protocol;

    @Override
    public void unsupported() {
        // set default protocol,when the client-side can't support spdy protocol
        protocol = "http/1.1";
    }

    @Override
    public List<String> protocols() {
        return PROTOCOLS;
    }

    @Override
    public void protocolSelected(String protocol) {
        this.protocol = protocol;
    }

    public String getSelectedProtocol() {
        return protocol;
    }
}
