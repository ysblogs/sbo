package com.yyy.service;

import com.yyy.util.R;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //很重要，必须添加
public class PowerFeignFallBackFactory implements FallbackFactory<PowerFeignClient> {
    private Logger logger = LoggerFactory.getLogger(PowerFeignFallBackFactory.class);
    @Override
    public PowerFeignClient create(Throwable throwable) {
        logger.error("PowerFeignClient fallback reason was: {}", throwable.getMessage());
        return new PowerFeignClient() {
            @Override
            public Object getPower() {
                String message = throwable.getMessage();
                return R.error("feign降级");
            }
        };
    }
}
