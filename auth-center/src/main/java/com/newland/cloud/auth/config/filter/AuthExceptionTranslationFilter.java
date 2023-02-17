package com.newland.cloud.auth.config.filter;

import com.alibaba.fastjson2.JSON;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.model.RestResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.OnCommittedResponseWrapper;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 认证信息错误拦截
 * Author: leell
 * Date: 2023/2/17 14:44:18
 */
public class AuthExceptionTranslationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            chain.doFilter(request,response);
        }catch (Exception e){
            e.printStackTrace();
            OnCommittedResponseWrapper httpResponse= (OnCommittedResponseWrapper) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            httpResponse.setStatus(HttpStatus.OK.value());
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                RestResponse restResponse = RestResponse.error(businessException.getCode(), businessException.getMessage());
                response.getWriter().write(JSON.toJSONString(restResponse));
            } else {
                RestResponse restResponse = RestResponse.error(e.getMessage());
                response.getWriter().write(JSON.toJSONString(restResponse));
            }
        }
    }
}
