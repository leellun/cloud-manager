package com.newland.cloud.system.controller;


import com.newland.cloud.model.RestResponse;
import com.newland.cloud.system.entity.SysMenu;
import com.newland.cloud.system.model.vo.MenuVo;
import com.newland.cloud.system.service.SysMenuService;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.IntOptions;
import com.newland.cloud.validator.Update;
import com.newland.mybatis.page.PageEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Tag(name =  "系统：菜单管理")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    @Operation(summary="获取所有菜单")
    @GetMapping("/catalogue")
    public RestResponse getUserMenus() {
        return RestResponse.ok(sysMenuService.getUserMenus());
    }
    @Operation(summary="获取所有权限")
    @GetMapping("/permission")
    public RestResponse getUserPermissions() {
        return RestResponse.ok(sysMenuService.getUserPermissions());
    }
    @Operation(summary="查询菜单分页")
    @GetMapping
    @PreAuthorize("hasAuthority('menu:select')")
    public RestResponse page(@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize) {
        return RestResponse.ok(sysMenuService.getMenuPage(PageEntity.page(pageNo,pageSize)));
    }
    @Operation(summary="返回子菜单")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('menu:select')")
    public RestResponse<MenuVo> getMenu(@PathVariable Long id) {
        return RestResponse.ok(sysMenuService.getMenu(id));
    }
    @Operation(summary="返回子菜单")
    @GetMapping(value = "/sub/{id}")
    @PreAuthorize("hasAnyAuthority('menu:select','role:select')")
    public RestResponse<Set<Long>> getSubMenus(@PathVariable Long id) {
        return RestResponse.ok(sysMenuService.getSubMenus(id));
    }
    @Operation(summary="新增菜单")
    @PostMapping
    @PreAuthorize("hasAuthority('menu:add')")
    public RestResponse add(@RequestBody @Validated(Insert.class) SysMenu sysMenu) {
        sysMenuService.addMenu(sysMenu);
        return RestResponse.success("添加成功");
    }

    @Operation(summary="修改菜单")
    @PutMapping
    @PreAuthorize("hasAuthority('menu:update')")
    public RestResponse update(@RequestBody @Validated(Update.class) SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return RestResponse.success("修改成功");
    }

    @Operation(summary="删除菜单")
    @DeleteMapping
    @PreAuthorize("hasAuthority('menu:delete')")
    public RestResponse delete(@RequestBody Set<Long> ids) {
        sysMenuService.deleteMenu(ids);
        return RestResponse.success("删除成功");
    }
    @Operation(summary="修改菜单排序")
    @PutMapping("/sort/{id}")
    @PreAuthorize("hasAuthority('menu:update')")
    public RestResponse updateDeptSort(@PathVariable("id") Long id, @RequestParam("menuSort") @Validated @Min(value = 1, message = "不能小于1") @Max(value = 1000, message = "不能大于1000") Integer menuSort) {
        sysMenuService.updateMenuSort(id, menuSort);
        return RestResponse.success("更新成功");
    }
    @Operation(summary="修改菜单状态")
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse enable(@PathVariable("id") Long id, @RequestParam("enable") @Validated @IntOptions(options = {0, 1}, message = "状态不正确") Integer enable) {
        sysMenuService.enableMenu(id, enable);
        return RestResponse.success("更新成功");
    }

}

