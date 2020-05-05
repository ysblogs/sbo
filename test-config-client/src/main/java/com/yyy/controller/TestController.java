package com.yyy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class TestController {

    @Value("${demo.hello}") //使用@Value注解来获取server端参数的值
    private String gitConfigName;

    @Value("${demo2.hello}")
    private String hello;


    @GetMapping("/hello")
    public String fromGitHubServer(){
        return gitConfigName;
    }

    @RequestMapping("/hello2")
    public String from() {
        return this.hello;
    }
}
