package com.example;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
@Slf4j
public class WebClientConfig {
    @Autowired
    private WebClientProperties webClientProperties;


    @Bean
    public WebClient webClient() throws Exception {

        //配置固定大小连接池
        ConnectionProvider provider = ConnectionProvider

                .builder("tax-core")
                // 等待超时时间
                .pendingAcquireTimeout(Duration.ofSeconds(webClientProperties.getPendingAcquireTimeout()))
                // 最大连接数
                .maxConnections(webClientProperties.getMaxConnections())
                // 等待队列大小
                .pendingAcquireMaxCount(webClientProperties.getPendingAcquireMaxCount()).build();

        SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        HttpClient httpClient = HttpClient.create(provider)
                //底层使用Netty时，可以如下配置超时时间
                .doOnConnected(conn -> conn
                        //Netty底层 读写超时设置
                        .addHandlerLast(new ReadTimeoutHandler(100)).addHandlerLast(new WriteTimeoutHandler(100)))

                .responseTimeout(Duration.ofSeconds(200))

                //连接超时设置
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000).option(ChannelOption.TCP_NODELAY, true).secure(t -> t.sslContext(sslContext));

        // 使用Reactor
        WebClient webClient1 = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(logRequest())
                .build();

        return webClient1;

    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("Request: {}={}", name, value)));


            return next.exchange(clientRequest);
        };
    }

}
