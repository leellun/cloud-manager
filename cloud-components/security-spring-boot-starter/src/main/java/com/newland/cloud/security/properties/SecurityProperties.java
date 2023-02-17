package com.newland.cloud.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: leell
 * Date: 2022/12/3 20:54:21
 */
@Data
@ConfigurationProperties(prefix = "newland.security")
public class SecurityProperties {
    private HttpItem[] permitItems;
}
