package com.newland.cloud.system.controller;


import com.newland.cloud.model.RestResponse;
import com.newland.cloud.system.entity.SysDepartment;
import com.newland.cloud.system.model.dto.DeptQueryDTO;
import com.newland.cloud.system.service.SysDepartmentService;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.IntOptions;
import com.newland.cloud.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Tag(name = "系统：部门管理")
@RestController
@RequestMapping("/dept")
public class SysDepartmentController {
    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Operation(summary = "查询部门列表")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse query(@Parameter(description = "查询部门") @RequestBody  DeptQueryDTO deptQueryDTO) {
        return RestResponse.ok(sysDepartmentService.getDepartments(deptQueryDTO));
    }
    @Operation(summary="查询部门")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('dept:select')")
    public RestResponse get(@Parameter(description = "部门id")@PathVariable Long id) {
        return RestResponse.ok(sysDepartmentService.getDepartment(id));
    }

    @Operation(summary="查询部门:获取同级与上级数据")
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse getDepartSearch(@Parameter(description = "部门名称") @RequestParam("name") String name) {
        return RestResponse.ok(sysDepartmentService.getDepartSearch(name));
    }

    @Operation(summary="查询部门:获取子部门")
    @GetMapping("/sub/{pid}")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse<List<SysDepartment>> getSubDepts(@Parameter(description = "部门父id") @PathVariable("pid") Long pid) {
        return RestResponse.ok(sysDepartmentService.getSubDepts(pid));
    }

    @Operation(summary="新增部门")
    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    public RestResponse add(@Parameter(description = "部门") @Validated(Insert.class) @RequestBody SysDepartment department) {
        sysDepartmentService.addDepartment(department);
        return RestResponse.success("添加成功");
    }

    @Operation(summary="修改部门")
    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse update(@Parameter(description = "部门") @Validated(Update.class) @RequestBody SysDepartment department) {
        sysDepartmentService.updateDepartment(department);
        return RestResponse.success("修改成功");
    }

    @Operation(summary="修改部门状态")
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse enable(@Parameter(description = "部门id") @PathVariable("id") Long id,@Parameter(description = "状态")  @RequestParam("enable") @Validated @IntOptions(options = {0, 1}, message = "状态不正确") Integer enable) {
        sysDepartmentService.enableDepartment(id, enable);
        return RestResponse.success("更新成功");
    }

    @Operation(summary="修改部门排序")
    @PutMapping("/sort/{id}")
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse updateDeptSort(@Parameter(description = "部门id") @PathVariable("id") Long id, @Parameter(description = "排序") @RequestParam("deptSort") @Validated @Min(value = 1, message = "不能小于1") @Max(value = 1000, message = "不能大于1000") Integer deptSort) {
        sysDepartmentService.updateDeptSort(id, deptSort);
        return RestResponse.success("更新成功");
    }

    @Operation(summary="删除部门")
    @DeleteMapping
    @PreAuthorize("hasAuthority('dept:delete')")
    public RestResponse delete(@Parameter(description = "部门id列表") @RequestBody Set<Long> ids) {
        sysDepartmentService.deleteDepartment(ids);
        return RestResponse.success("删除成功");
    }
}

