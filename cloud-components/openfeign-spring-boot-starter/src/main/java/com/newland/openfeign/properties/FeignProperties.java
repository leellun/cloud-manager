package com.newland.openfeign.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Author: leell
 * Date: 2022/12/4 19:31:37
 */
@Data
@ConfigurationProperties(prefix = "newland.feign")
public class FeignProperties {
    private Boolean concurrency;
}
