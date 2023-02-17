package com.newland.openfeign;

import com.newland.openfeign.properties.FeignProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Author: leell
 * Date: 2022/11/27 18:58:00
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(value = FeignProperties.class)
public class OpenfeignConfiguration {
}
