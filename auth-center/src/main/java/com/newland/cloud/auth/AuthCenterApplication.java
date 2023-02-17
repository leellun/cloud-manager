package com.newland.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author leell
 */
@SpringBootApplication(scanBasePackages = "com.newland.cloud")
@EnableFeignClients(basePackages = "com.newland.cloud")
@EnableDiscoveryClient
public class AuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthCenterApplication.class, args);
    }

}
