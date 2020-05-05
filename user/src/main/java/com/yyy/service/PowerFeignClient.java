package com.yyy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(name = "SERVER-POWER",fallback = PowerFeignFallBack.class)
@FeignClient(value = "SERVER-POWER",fallbackFactory = PowerFeignFallBackFactory.class)
public interface PowerFeignClient {

    @RequestMapping("/getPower.do")
    public Object getPower();
}
