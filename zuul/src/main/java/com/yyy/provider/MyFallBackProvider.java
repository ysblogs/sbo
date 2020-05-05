package com.yyy.provider;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component //Spring容器，必须添加
public class MyFallBackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        //制定为哪个微服务提供回退（可以写微服务名server-power ,也可写*代表所有微服务）
        return "*";
    }

    /**
     * ClientHttpResponse是一个接口，具体的回退逻辑要实现此接口
     *
     * @param route 出错的微服务名
     * @param cause 出错的异常对象
     * @return 返回一个ClientHttpResponse对象
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        //这里可以判断根据不同的异常来做不同的处理， 也可以不判断
        //完了之后调用response方法并根据异常类型传入HttpStatus
        if (cause instanceof HystrixTimeoutException) {
            //   GATEWAY_TIMEOUT(504, "Gateway Timeout"),
            return this.response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            //  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
            return this.response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ClientHttpResponse response(final HttpStatus status) {
        //这里返回一个ClientHttpResponse对象 并实现其中的方法，关于回退逻辑的详细，便在下面的方法中
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //返回一个HttpStatus对象 这个对象是个枚举对象， 里面包含了一个status code 和reasonPhrase信息
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                //返回status的code  比如 404，500等
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                //返回一个HttpStatus对象的reasonPhrase信息，即响应状态码对应的文本
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
                //close的时候调用的方法， 就是当降级信息全部响应完了之后调用的方法
            }

            @Override
            public InputStream getBody() throws IOException {
                //把降级信息响应回前端
                return new ByteArrayInputStream("降级信息".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                //需要对响应报头设置的话可以在此设置
                HttpHeaders headers = new HttpHeaders();
                //public static final MediaType APPLICATION_JSON = new MediaType("application", "json");
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}

