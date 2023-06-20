package com.example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("web.client")
@Data
@Configuration
public class WebClientProperties {

    /**
     * 超时时间 单位秒
     */
    private int pendingAcquireTimeout = 10;

    /**
     * 最大等待任务数
     * */
    private int pendingAcquireMaxCount = 10000;

    /**
     * 连接池最大连接数
     */
    private int maxConnections = 1000;

}