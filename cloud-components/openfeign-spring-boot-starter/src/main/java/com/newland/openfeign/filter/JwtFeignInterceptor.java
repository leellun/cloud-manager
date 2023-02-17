package com.newland.openfeign.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtFeignInterceptor implements RequestInterceptor {

    private final String key = "json-token";


    @Override
    public void apply(RequestTemplate template) {
        if (!template.headers().containsKey(key)) {
            String value = getHeaderValue(key);
            if (value != null) {
                try{
                    template.header(key, value);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private String getHeaderValue(String key) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getHeader(key);
    }
}
