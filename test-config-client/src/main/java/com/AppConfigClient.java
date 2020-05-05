package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//激活对注册中心的支持
@EnableEurekaClient
public class AppConfigClient {
    public static void main(String[] args) {
        SpringApplication.run(AppConfigClient.class);
    }
}
