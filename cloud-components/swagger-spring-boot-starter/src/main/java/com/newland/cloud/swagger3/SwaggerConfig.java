package com.newland.cloud.swagger3;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.newland.cloud.swagger3.properties.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * swagger配置
 *
 * @author leell
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableKnife4j
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", new Random().nextInt(1, 100));
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.getPaths().addExtension("x-abb", new Random().nextInt(1, 100));
            }
        };
    }

    /***
     * 接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail(swaggerProperties.getConcat().getEmail());
        contact.setName(swaggerProperties.getConcat().getName());
        contact.setUrl(swaggerProperties.getConcat().getUrl());
        return new OpenAPI()
                .info(new Info()
                        .title(swaggerProperties.getTitle())
                        .version(swaggerProperties.getVersion())
                        .description(swaggerProperties.getVersion())
                        .contact(contact));
    }
}
