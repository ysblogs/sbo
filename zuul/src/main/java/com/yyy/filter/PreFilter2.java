package com.yyy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component //Spring容器，必须添加
public class PreFilter2 extends ZuulFilter {
    @Override
    public String filterType() {
//        public static final String PRE_TYPE = "pre";
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object msg = ctx.get("msg");
        System.out.println("第二个自定义过滤器接收信息："+msg);
        return null;
    }
}
