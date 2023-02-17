package com.newland.cloud.swagger3.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lanhe.swagger")
@Data
public class SwaggerProperties {
    private Boolean enable;
    private String version;
    private String title;
    private String basePackage;
    private String description;
    private Concat concat;

    @Data
    public static class Concat {
        private String name;
        private String url;
        private String email;
    }
}
