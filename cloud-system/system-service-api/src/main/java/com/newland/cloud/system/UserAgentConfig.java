package com.newland.cloud.system;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Author: leell
 * Date: 2023/1/15 17:27:09
 */
@Configuration
public class UserAgentConfig {
    @Bean
    public ReactiveResilience4JCircuitBreakerFactory r4jFactory(CircuitBreakerRegistry circuitBreakerRegistry,
                                                                TimeLimiterRegistry timeLimiterRegistry) {
        // 断路器配置
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                // 滑动窗口的类型为时间窗口
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)
                // 时间窗口的大小为60秒
                .slidingWindowSize(60)
                // 在单位时间窗口内最少需要10次调用才能开始进行统计计算
                .minimumNumberOfCalls(10)
                // 在单位时间窗口内调用失败率达到60%后会启动断路器
                .failureRateThreshold(60)
                // 允许断路器自动由打开状态转换为半开状态
                .enableAutomaticTransitionFromOpenToHalfOpen()
                // 在半开状态下允许进行正常调用的次数
                .permittedNumberOfCallsInHalfOpenState(5)
                // 断路器打开状态转换为半开状态需要等待60秒
                .waitDurationInOpenState(Duration.ofSeconds(60))
                // 当作失败处理的异常类型
                .recordExceptions(Throwable.class)
                .build();
        // 超时配置
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                // 设置超时时间为60s
                .timeoutDuration(Duration.ofSeconds(60))
                .build();
        ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry);
        circuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig).build());
        return circuitBreakerFactory;
    }
}
