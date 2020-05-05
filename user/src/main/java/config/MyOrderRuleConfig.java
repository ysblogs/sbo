package config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认轮询算法的配置类
 *
 * 注意：（该类不能和主程序类在同一层目录结构或者在子包下）
 */
@Configuration
public class MyOrderRuleConfig {
    @Bean
    public IRule iRule(){
        System.out.println("#############################使用默认的负载均衡策略#############################");
        return  new RoundRobinRule();
    }
}
