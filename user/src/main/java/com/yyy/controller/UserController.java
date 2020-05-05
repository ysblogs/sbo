package com.yyy.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yyy.service.PowerFeignClient;
import com.yyy.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    //这里直接注入feign client
    @Autowired
    private PowerFeignClient powerFeignClient;

    //声明微服务名SERVER-POWER，包含两个微服务：power-0,power-1
    private static  final String POWER_URL="http://SERVER-POWER";
    //声明微服务名SERVER-ORDER，包含两个微服务：order-0,order-1
    private static  final String ORDER_URL="http://SERVER-ORDER";

    @RequestMapping("/getUser.do")
    public R getUser(){
        Map<String,Object> map = new HashMap<>();
        map.put("key","user");
        return R.success("返回成功",map);
    }


    @RequestMapping("/getOrder.do")
    public R getOrder(){
        return R.success("操作成功",restTemplate.getForObject(ORDER_URL+"/getOrder.do",Object.class));
    }

    @RequestMapping("/getFeignPower.do")
    public R getFeignPower(String name){
        return R.success("操作成功",powerFeignClient.getPower());
    }

    @RequestMapping("/getPower.do")
    @HystrixCommand(//fallbackMethod = "myFallbackMethod",//此注解表示此方法是hystrix方法，其中fallbackMethod定义回退方法的名称
            threadPoolKey = "power",//线程池唯一标识, hystrix 会拿你这个标识去计数，看线程占用是否超过了， 超过了就会直接降级该次调用
            threadPoolProperties ={
                    @HystrixProperty(name = "coreSize",value = "5")//并发执行的最大线程数，默认值为 10。
            })
    public R getPower(String name){
        System.out.println("调用了该方法");
        return R.success("操作成功",restTemplate.getForObject(POWER_URL+"/getPower.do",Object.class));
    }

    //该方法是getFeignPower的回退方法，可以指定将hystrix执行失败异常传入到方法中
//    public R myFallbackMethod(String name){//返回R，其实就是一个Map
//        System.out.println("##########################name="+name);
//        return R.error("系统正在维护中，请稍后重试！");
//    }
}
