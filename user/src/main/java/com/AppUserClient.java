package com;

import config.MyOrderRuleConfig;
import config.MyPowerRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 配置本应用将使用服务注册和服务发现
@EnableEurekaClient
//为服务SERVER-ORDER、SERVER-POWER指定自定义的负载均衡算法
@RibbonClients({
        @RibbonClient(name = "SERVER-ORDER",configuration = MyOrderRuleConfig.class),
        @RibbonClient(name = "SERVER-POWER",configuration = MyPowerRuleConfig.class)
})
//开启Http客户端Feign
@EnableFeignClients
//启动断路器(@EnableHystrix 中包含了 @EnableCircuitBreaker)
@EnableHystrix
public class AppUserClient {
    public static void main(String[] args) {
        SpringApplication.run(AppUserClient.class);
    }
}
