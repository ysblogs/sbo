package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//激活对配置中心的支持
@EnableConfigServer
//Eureka激活对注册中心的支持
@EnableEurekaClient
public class AppConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(AppConfigServer.class);
    }
}
