package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TraceService {


    @Async("jdkCustomExecutor")
    public void test1() {

        log.info("trace service log");

    }



    @Async("jdkCustomExecutor")
    public String test2() {

        log.info("trace service log");

        return "test";
    }

}
