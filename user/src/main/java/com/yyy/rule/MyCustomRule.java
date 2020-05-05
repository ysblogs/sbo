package com.yyy.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

/**
 * 自定义负载均衡策略_伪随机
 */
public class MyCustomRule extends AbstractLoadBalancerRule {
    Random rand;
    private int nowIndex = -1;//当前下标,因为默认是0，所以此处设置-1
    private int lastIndex = -1;//上次下标
    private int skipIndex = -1;//跳过的下标

    public MyCustomRule() {
        rand = new Random();
    }

    /**
     * 伪随机，当一个下标（微服务） 连续被调用两次， 第三次如果还是它， 那么咱们就再随机一次。
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {//线程是否中断
                return null;
            }
            List<Server> upList = lb.getReachableServers();//存活的服务
            List<Server> allList = lb.getAllServers();//所有的服务

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            int index = rand.nextInt(serverCount);
            System.out.println("服务是："+allList.get(index).getHostPort()+",当前下标:"+index);
            if (index==skipIndex){
//                System.out.println("服务是："+allList.get(index).getHostPort()+",跳过");
                index =rand.nextInt(serverCount);//重新随机一次
                lastIndex = -1;
//                System.out.println("服务是："+allList.get(index).getHostPort()+",跳过之后的下标:"+index);
            }

            skipIndex = -1;//不需要跳过
            nowIndex = index;
            if(nowIndex==lastIndex){
//                System.out.println("服务是："+allList.get(index).getHostPort()+",需要跳过的下标："+nowIndex);
                skipIndex = nowIndex;
            }

            server = upList.get(index);//从存活的列表中获取server
            lastIndex = nowIndex;//随机完成后，这次的下标就是上次的下标

            if (server == null) {//如果获取到的服务实例为空
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();//线程让步
                continue;//调到下一次循环(server == null）会为真继续进入while循环
            }

            if (server.isAlive()) { //如果服务实例是存活的则返回该服务实例
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }
}
