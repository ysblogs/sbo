package com.yyy.service;

import com.yyy.util.R;
import org.springframework.stereotype.Component;

@Component //很重要，必须添加
public class PowerFeignFallBack implements PowerFeignClient{
    @Override
    public Object getPower() {
        return R.error("Power服务暂时不可用！");
    }
}
