package com.newland.cloud.system.agent;


import com.newland.cloud.model.RestResponse;
import com.newland.cloud.model.LoginUser;
import com.newland.cloud.system.dto.LoginDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@FeignClient("system-service")
public interface SysUserApiAgent {

    @PostMapping(value = "/user/login")
    @CircuitBreaker(name = "system-login")
    RestResponse<LoginUser> login(@RequestBody LoginDTO loginDTO);

}

