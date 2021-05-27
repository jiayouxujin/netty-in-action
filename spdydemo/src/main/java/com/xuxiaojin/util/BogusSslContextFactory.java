package com.xuxiaojin.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;

public class BogusSslContextFactory {
    private static final String PROTOCOL = "TLS";
    private static final SSLContext SERVER_CONTEXT;

    static {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }

        SSLContext serverContext = null;

        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(BogusKeyStore.asInputStream(), BogusKeyStore.getKeyStorePassword());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, BogusKeyStore.getCertificatePassword());

            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SERVER_CONTEXT = serverContext;
    }

    private BogusSslContextFactory() {

    }

    public static SSLContext getServerContext() {
        return SERVER_CONTEXT;
    }
}
