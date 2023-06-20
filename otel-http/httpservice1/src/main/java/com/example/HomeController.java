package com.example;

import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.otel.bridge.OtelTraceContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@RestController
@Validated
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    Tracer tracer;


    @Autowired
    WebClient webClient;

    @Autowired
    Executor customExecutor;

    @Autowired
    TraceService traceService;

    public HomeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    static Set<Long> set = new HashSet<>();


    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("/")
    public String service() {

        Span span = tracer.spanBuilder().name("carson1").setParent(tracer.currentSpan().context()).start();
        span.tag("test", "mytest");
        Span error = span.error(new RuntimeException("error 11"));

        span.event("test event");
        span.end();
        log.info("Hit service 1");

//        redisTemplate.opsForValue().set("traceTest", "trace test");
        redisTemplate.opsForValue().setIfAbsent("traceTest", "trace test", 1000, TimeUnit.SECONDS);

        customExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                Span span = tracer.spanBuilder().name("carson 2").setParent(tracer.currentSpan().context()).start();
//                span.tag("test", "mytest");
//                span.error(new RuntimeException("error 11"));
//
//                span.event("test event");
//                span.end();
                log.info("sdasd");
            }
        });

        traceService.test1();
        traceService.test2();
//        throw new RuntimeException("ex");

//        return this.restTemplate.getForObject("http://localhost:8081", String.class);

        Mono<String> mono = webClient.get().uri("http://localhost:8081")
                .retrieve()
                .bodyToMono(String.class)// 异常处理
                .doOnError(WebClientException.class, err -> {
                    System.out.println(err.getMessage() + "," + err.getMessage());
                    throw new RuntimeException(err.getMessage());
                });
        String block = mono.block();

        return block;
    }

    @PostMapping("/1")
    String service1(@Valid @RequestBody Person person) {
        long id = Thread.currentThread().getId();
        System.out.println(id);
        if (set.contains(id)) {
            System.out.println("carson");
        }


        set.add(id);

        return "asdfasdf";
    }


}
