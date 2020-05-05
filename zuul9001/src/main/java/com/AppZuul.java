package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//路由注解(注意：@EnableZuulProxy与@EnableZuulServer区别)
public class AppZuul {
    public static void main(String[] args) {
        SpringApplication.run(AppZuul.class);
    }
}
