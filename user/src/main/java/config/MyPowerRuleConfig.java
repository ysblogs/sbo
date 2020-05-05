package config;

import com.netflix.loadbalancer.IRule;
import com.yyy.rule.MyCustomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义的算法的配置类
 *
 * 注意：（该类不能和主程序类在同一层目录结构或者在子包下）
 */
@Configuration
public class MyPowerRuleConfig {

    @Bean
    public IRule iRule(){
        System.out.println("#############################使用自定义伪随机负载均衡策略#############################");
        return  new MyCustomRule();
    }
}
