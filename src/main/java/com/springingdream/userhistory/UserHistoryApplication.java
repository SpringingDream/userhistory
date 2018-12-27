package com.springingdream.userhistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserHistoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserHistoryApplication.class, args);
    }
}

