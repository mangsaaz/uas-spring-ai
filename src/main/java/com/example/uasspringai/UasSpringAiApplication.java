package com.example.uasspringai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class UasSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UasSpringAiApplication.class, args);
    }
}