package com.newland.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.newland.cloud.model.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * 注入公共字段自动填充,任选注入方式即可
 */
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) token.getPrincipal();
            if (loginUser != null) {
                if (metaObject.hasGetter("createdBy")) {
                    setFieldValByName("createdBy", loginUser.getUserId(), metaObject);
                }
            }
        }
        if (metaObject.hasGetter("gmtCreate")) {
            setFieldValByName("gmtCreate", LocalDateTime.now().withNano(0), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            LoginUser loginUser = (LoginUser) token.getPrincipal();
            if (loginUser != null) {
                if (metaObject.hasGetter("updatedBy")) {
                    setFieldValByName("updatedBy", loginUser.getUserId(), metaObject);
                }
            }
        }
        if (metaObject.hasGetter("gmtModified")) {
            setFieldValByName("gmtModified", LocalDateTime.now().withNano(0), metaObject);
        }
    }
}
