package com.newland.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.cloud.enumeration.BasicEnum;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.system.entity.SysJob;
import com.newland.cloud.system.enums.UserServiceErrorEnum;
import com.newland.cloud.system.mapper.SysJobMapper;
import com.newland.cloud.system.model.dto.JobQueryDTO;
import com.newland.cloud.system.service.SysJobService;
import com.newland.cloud.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 岗位 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    @Override
    public List<SysJob> getAllJobs() {
        return baseMapper.selectList(Wrappers.<SysJob>lambdaQuery().eq(SysJob::getEnabled,BasicEnum.YES.getCode()).orderByAsc(SysJob::getJobSort));
    }

    @Override
    public Page<SysJob> getJobs(JobQueryDTO jobQueryDTO) {
        Page<SysJob> page = PageWrapper.wrapper(jobQueryDTO);
        LambdaQueryWrapper<SysJob> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(jobQueryDTO.getName())) {
            wrapper.like(SysJob::getName, jobQueryDTO.getName());
        }
        if (jobQueryDTO.getEnabled() != null) {
            wrapper.eq(SysJob::getEnabled, jobQueryDTO.getEnabled());
        }
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void addJob(SysJob sysJob) {
        SysJob dbJob = baseMapper.selectOne(Wrappers.<SysJob>lambdaQuery().eq(SysJob::getName, sysJob.getName()));
        AssertUtil.isNull(dbJob, UserServiceErrorEnum.JOB_EXIST);
        sysJob.setEnabled(BasicEnum.YES.getCode());
        baseMapper.insert(sysJob);
    }

    @Override
    public void updateJob(SysJob sysJob) {
        SysJob dbJob = baseMapper.selectById(sysJob.getId());
        AssertUtil.notNull(dbJob, UserServiceErrorEnum.JOB_NOT_EXIST);
        baseMapper.update(new SysJob(), Wrappers.<SysJob>lambdaUpdate()
                .set(SysJob::getJobSort, sysJob.getJobSort())
                .eq(SysJob::getId, sysJob.getId()));
    }

    @Override
    public void deleteJob(Set<Long> ids) {
        int count = baseMapper.deleteBatchIds(ids);
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.JOB_DELETE_FAIL);
    }

    @Override
    public void enable(Long id, Integer enable) {
        SysJob dbJob = baseMapper.selectById(id);
        AssertUtil.notNull(dbJob, UserServiceErrorEnum.JOB_NOT_EXIST);
        baseMapper.update(new SysJob(), Wrappers.<SysJob>lambdaUpdate()
                .set(SysJob::getEnabled, enable)
                .eq(SysJob::getId, id));
    }
}
