package com.newland.cloud.system.controller;


import com.newland.cloud.model.RestResponse;
import com.newland.cloud.system.entity.SysJob;
import com.newland.cloud.system.model.dto.JobQueryDTO;
import com.newland.cloud.system.service.SysJobService;
import com.newland.cloud.validator.Insert;
import com.newland.cloud.validator.IntOptions;
import com.newland.cloud.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 岗位 前端控制器
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Tag(name =  "系统：岗位管理")
@RestController
@RequestMapping("/job")
public class SysJobController {

    @Autowired
    private SysJobService sysJobService;
    @Operation(summary="返回全部的岗位")
    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('job:select','user:select')")
    public RestResponse all() {
        return RestResponse.ok(sysJobService.getAllJobs());
    }
    @Operation(summary="返回岗位")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('job:select')")
    public RestResponse get(@PathVariable Long id) {
        return RestResponse.ok(sysJobService.getById(id));
    }
    @Operation(summary="查询岗位")
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('job:select','user:select')")
    public RestResponse list(@RequestBody JobQueryDTO jobQueryDTO) {
        return RestResponse.ok(sysJobService.getJobs(jobQueryDTO));
    }

    @Operation(summary="新增岗位")
    @PostMapping
    @PreAuthorize("hasAuthority('job:add')")
    public RestResponse add(@RequestBody @Validated(Insert.class) SysJob sysJob) {
        sysJobService.addJob(sysJob);
        return RestResponse.success("添加岗位成功");
    }

    @Operation(summary="修改岗位")
    @PutMapping
    @PreAuthorize("hasAuthority('job:update')")
    public RestResponse update(@RequestBody @Validated(Update.class) SysJob sysJob) {
        sysJobService.updateJob(sysJob);
        return RestResponse.success("更新岗位成功");
    }
    @Operation(summary="修改岗位状态")
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('job:update')")
    public RestResponse enable(@PathVariable("id") Long id,@RequestParam("enable") @Validated @IntOptions(options = {0,1},message = "状态不正确") Integer enable) {
        sysJobService.enable(id,enable);
        return RestResponse.success("更新状态成功");
    }

    @Operation(summary="删除岗位")
    @DeleteMapping
    @PreAuthorize("hasAuthority('job:delete')")
    public RestResponse delete(@RequestBody Set<Long> ids) {
        sysJobService.deleteJob(ids);
        return RestResponse.success("删除岗位成功");
    }
}

