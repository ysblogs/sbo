package com.yyy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component //Spring容器，必须添加
public class LogFilter extends ZuulFilter {
    /**
     * 返回过滤器的类型,有 pre、 route、 post、 error等几种取值，分别对应Zuul的几种过滤器。
     * @return
     */
    @Override
    public String filterType() {
//        public static final String ROUTE_TYPE = "route";
        return FilterConstants.ROUTE_TYPE;//此处设置
    }

    /**
     * 返回一个 int值来指定过滤器的执行顺序，不同的过滤器允许返回相同的数字。
     *
     * 数值越小，优先级越高。
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * 返回一个 boolean值来判断该过滤器是否要执行， true表示执行， false表示不执行。
     *
     * 这个也可以利用配置中心来实现，达到动态的开启和关闭过滤器。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *过滤器执行逻辑, 返回任意Object类型的值,都表示放行,包括null
     *
     * 如果不想往后继续执行了,就使用currentContext.setSendZuulResponse(false);
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取当前请求对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //2.获取出请求头
        String header = request.getHeader("Authorization");
        System.out.println("header:"+header);
        //获取请求头中的token
        String token = request.getHeader("token");
        System.out.println("token:"+token);
        //获取URI
        String requestURI = request.getRequestURI();
        System.out.println(String.format("send %s request to %s",request.getMethod(),requestURI));
        //获取地址
        String remoteAddr = request.getRemoteAddr();
        if (request.getHeader("x-forwarded-for") == null) {
            System.out.println("IPv6的地址："+remoteAddr);
        }else{
            System.out.println("客户端真实IP地址："+request.getHeader("x-forwarded-for"));
        }

        //public static final String REQUEST_URI_KEY = "requestURI";
        String s = currentContext.get(FilterConstants.REQUEST_URI_KEY).toString();
        System.out.println("访问者IP："+remoteAddr+" 访问地址:"+requestURI+" 路由后的地址："+s);
        return null;
    }
}