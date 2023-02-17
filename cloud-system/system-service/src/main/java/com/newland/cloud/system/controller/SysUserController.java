package com.newland.cloud.system.controller;


import com.newland.cloud.model.LoginUser;
import com.newland.cloud.model.RestResponse;
import com.newland.cloud.system.dto.LoginDTO;
import com.newland.cloud.system.model.dto.SysUserDto;
import com.newland.cloud.system.model.dto.UserCenterDTO;
import com.newland.cloud.system.model.dto.UserPassVO;
import com.newland.cloud.system.model.dto.UserQueryDTO;
import com.newland.cloud.system.service.SysUserService;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.IntOptions;
import com.newland.cloud.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Tag(name = "系统：用户管理")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "用户登录")
    @PostMapping(value = "/login")
    public RestResponse<LoginUser> login(@Parameter(description = "登录信息") @RequestBody @Validated LoginDTO loginDTO) {
        return RestResponse.ok(sysUserService.login(loginDTO));
    }

    @Operation(summary = "查询用户")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('user:select')")
    public RestResponse list(@Parameter(description = "登录信息") @RequestBody UserQueryDTO userQueryDTO) {
        return RestResponse.ok(sysUserService.getUsers(userQueryDTO));
    }

    @Operation(summary = "查询用户")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:select')")
    public RestResponse getUser(@Parameter(description = "用户id") @PathVariable Long id) {
        return RestResponse.ok(sysUserService.getUser(id));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    public RestResponse add(@Parameter(description = "用户") @RequestBody @Validated(Insert.class) SysUserDto sysUserDto) {
        sysUserService.addUser(sysUserDto);
        return RestResponse.success("添加成功");
    }

    @Operation(summary = "修改用户")
    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse update(@Parameter(description = "用户") @RequestBody @Validated(Update.class) SysUserDto sysUserDto) {
        sysUserService.updateUser(sysUserDto);
        return RestResponse.success("更新成功");
    }

    @Operation(summary = "修改用户状态")
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse enable(@Parameter(description = "用户id") @PathVariable("id") Long id, @Parameter(description = "状态") @RequestParam("enable") @Validated @IntOptions(options = {0, 1}, message = "状态不正确") Integer enable) {
        sysUserService.enableUser(id, enable);
        return RestResponse.success("更新成功");
    }

    @Operation(summary = "修改用户：个人中心")
    @PutMapping(value = "center")
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse center(@Parameter(description = "个人中心") @RequestBody UserCenterDTO userCenterDTO) {
        sysUserService.updateCenter(userCenterDTO);
        return RestResponse.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    public RestResponse delete(@Parameter(description = "用户id列表") @RequestBody Set<Long> ids) {
        sysUserService.deleteUser(ids);
        return RestResponse.success("删除用户成功");
    }

    @Operation(summary = "修改密码")
    @PostMapping(value = "/updatePass")
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse updatePass(@Parameter(description = "用户密码") @RequestBody UserPassVO userPassVO) {
        sysUserService.updatePass(userPassVO);
        return RestResponse.success("密码修改成功");
    }

    @Operation(summary = "重置密码")
    @PutMapping(value = "/resetPass/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse resetPass(@Parameter(description = "用户id") @PathVariable Long id) {
        sysUserService.resetPass(id);
        return RestResponse.success("密码重置成功");
    }

    @Operation(summary = "上传头像")
    @PostMapping(value = "/updateAvatar")
    @PreAuthorize("hasAuthority('user:update')")
    public RestResponse<String> updateAvatar(@Parameter(description = "头像") @RequestParam String avatar) {
        sysUserService.updateAvatar(avatar);
        return RestResponse.success("头像上传成功");
    }
}

