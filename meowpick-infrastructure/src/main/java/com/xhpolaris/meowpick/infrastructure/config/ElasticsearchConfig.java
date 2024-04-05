package com.xhpolaris.meowpick.infrastructure.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ElasticsearchConfig implements RestClientBuilderCustomizer {

    @Override
    public void customize(RestClientBuilder builder) {

    }

    /**
     * 在这里加入自定义逻辑，比如跳过SSL的证书检查和hostname检查
     */
    @Override
    public void customize(HttpAsyncClientBuilder builder) {
        SSLContextBuilder sscb = SSLContexts.custom();
        try {
            // 在这里跳过证书信息校验
            sscb.loadTrustMaterial((chain, authType) -> true);
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            log.error("RestClientBuilderCustomizerImpl error", e);
        }
        try {
            builder.setSSLContext(sscb.build());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            log.error("RestClientBuilderCustomizerImpl error", e);
        }
        // 这里跳过主机名称校验
        builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
    }
}