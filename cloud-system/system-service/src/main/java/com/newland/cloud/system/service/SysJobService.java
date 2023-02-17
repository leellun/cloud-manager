package com.newland.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.cloud.system.entity.SysJob;
import com.newland.cloud.system.model.dto.JobQueryDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 岗位 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysJobService extends IService<SysJob> {
    /**
     * 获取所有角色
     * @return
     */
    List<SysJob> getAllJobs();
    /**
     * 获取分页job
     * @param jobQueryDTO
     * @return
     */
    Page<SysJob> getJobs(JobQueryDTO jobQueryDTO);

    /**
     * 添加job
     * @param sysJob
     */
    void addJob(SysJob sysJob);

    /**
     * 修改job
     * @param sysJob
     */
    void updateJob(SysJob sysJob);

    /**
     * 删除job
     * @param ids job id列表
     */
    void deleteJob(Set<Long> ids);

    /**
     * 开启关闭
     * @param id
     */
    void enable(Long id,Integer enable);
}
