package com.newland.cloud.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: leell
 * Date: 2023/1/15 21:02:27
 */
@Data
@ConfigurationProperties
public class HttpItem{
    private String url;
    private String method;
}
