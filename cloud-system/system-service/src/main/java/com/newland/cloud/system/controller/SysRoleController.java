package com.newland.cloud.system.controller;


import com.newland.cloud.model.RestResponse;
import com.newland.cloud.system.entity.SysRole;
import com.newland.cloud.system.service.SysRoleService;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.IntOptions;
import com.newland.cloud.validator.Update;
import com.newland.cloud.system.model.dto.RoleQueryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Tag(name =  "系统：角色管理")
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary="获取单个role")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('role:select')")
    public RestResponse query(@PathVariable Long id) {
        return RestResponse.ok(sysRoleService.getRole(id));
    }

    @Operation(summary="返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('role:select','user:select')")
    public RestResponse all() {
        return RestResponse.ok(sysRoleService.getAllRoles());
    }

    @Operation(summary="查询角色")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('role:select')")
    public RestResponse list(@RequestBody RoleQueryDTO roleQueryDTO) {
        return RestResponse.ok(sysRoleService.getRolePage(roleQueryDTO));
    }

    @Operation(summary="新增角色")
    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    public RestResponse add(@RequestBody @Validated(Insert.class) SysRole sysRole) {
        sysRoleService.addRole(sysRole);
        return RestResponse.success("添加成功");
    }

    @Operation(summary="修改角色")
    @PutMapping
    @PreAuthorize("hasAuthority('role:update')")
    public RestResponse update(@RequestBody @Validated(Update.class) SysRole sysRole) {
        sysRoleService.updateRole(sysRole);
        return RestResponse.success("修改成功");
    }

    @Operation(summary="修改状态")
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public RestResponse enable(@PathVariable("id") Long id, @RequestParam("enable") @Validated @IntOptions(options = {0, 1}, message = "状态不正确") Integer enable) {
        sysRoleService.enableRole(id, enable);
        return RestResponse.success("更新成功");
    }

    @Operation(summary="删除角色")
    @DeleteMapping
    @PreAuthorize("hasAuthority('role:delete')")
    public RestResponse delete(@RequestBody Set<Long> ids) {
        sysRoleService.deleteRoles(ids);
        return RestResponse.success("删除成功");
    }

    @Operation(summary="添加权限")
    @PostMapping("/permission/{id}")
    @PreAuthorize("hasAuthority('role:add')")
    public RestResponse addMenuPermission(@PathVariable("id") Long id, @RequestBody Set<Long> permissions) {
        sysRoleService.addMenuPermission(id, permissions);
        return RestResponse.success("设置成功");
    }

    @Operation(summary="获取权限")
    @GetMapping("/permission/{id}")
    @PreAuthorize("hasAuthority('role:select')")
    public RestResponse getMenuPermission(@PathVariable("id") Long id) {
        List<Long> list = sysRoleService.getMenuPermission(id);
        return RestResponse.ok(list.stream().map(String::valueOf).collect(Collectors.toList()));
    }
}

