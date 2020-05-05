package com.yyy.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com") //可以不写
@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced //负载均衡 Ribbon
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

//    @Bean
//    public IRule iRule(){
//        System.out.println("#############################使用自定义伪随机负载均衡策略#############################");
//        return  new MyCustomRule();
//    }

//    @Bean
//    public TomcatServletWebServerFactory tomcat(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.setPort(5000);
//        return  tomcat;
//    }
}
