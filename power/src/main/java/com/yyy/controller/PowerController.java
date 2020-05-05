package com.yyy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PowerController {

    @RequestMapping("/getPower.do")
    public Object getPower(String name) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("key","power6000");
        //模拟异常
//        if (name==null){
//            throw new Exception();
//        }
        //模拟响应超时
//        Thread.sleep(10*1000);//休眠10s
        return map;
    }

}

