package com.newland.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.cloud.enumeration.BasicEnum;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.system.entity.SysDepartment;
import com.newland.cloud.system.enums.UserServiceErrorEnum;
import com.newland.cloud.system.mapper.SysDepartmentMapper;
import com.newland.cloud.system.model.dto.DeptQueryDTO;
import com.newland.cloud.system.model.vo.SysDepartmentVo;
import com.newland.cloud.system.service.SysDepartmentService;
import com.newland.cloud.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    @Override
    public SysDepartmentVo getDepartment(Long id) {
        SysDepartment dbDepartment = baseMapper.selectById(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST);
        SysDepartmentVo vo = new SysDepartmentVo();
        BeanUtils.copyProperties(dbDepartment, vo);
        if (dbDepartment.getPid() != null) {
            SysDepartment parent = baseMapper.selectOne(Wrappers.<SysDepartment>lambdaQuery().select(SysDepartment::getName).eq(SysDepartment::getId, dbDepartment.getPid()));
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        return vo;
    }

    @Override
    public Page<SysDepartment> getDepartments(DeptQueryDTO deptQueryDTO) {
        Page<SysDepartment> page = PageWrapper.wrapper(deptQueryDTO);
        LambdaQueryWrapper<SysDepartment> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(deptQueryDTO.getName())) {
            wrapper.like(SysDepartment::getName, deptQueryDTO.getName());
        }
        if (deptQueryDTO.getEnabled() != null) {
            wrapper.eq(SysDepartment::getEnabled, deptQueryDTO.getEnabled());
        }
        wrapper.isNull(SysDepartment::getPid);
        wrapper.orderByAsc(SysDepartment::getDeptSort);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void addDepartment(SysDepartment department) {
        LambdaQueryWrapper<SysDepartment> wrapper = Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getName, department.getName());
        if (department.getPid() != null) {
            SysDepartment parentDepartment = baseMapper.selectById(department.getPid());
            AssertUtil.notNull(parentDepartment, UserServiceErrorEnum.DEPARTMENT_PARENT_NOT_EXIST);
            wrapper.eq(SysDepartment::getPid, department.getPid());
        } else {
            wrapper.isNotNull(SysDepartment::getPid);
        }
        SysDepartment dbDepartment = baseMapper.selectOne(wrapper);
        AssertUtil.isNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_EXIST);
        department.setEnabled(BasicEnum.YES.getCode());
        department.setSubCount(0);
        baseMapper.insert(department);
        if (department.getPid() != null) {
            this.updateSubCount(department.getPid());
        }
    }

    @Override
    public void updateDepartment(SysDepartment department) {
        SysDepartment dbDepartment = baseMapper.selectById(department.getId());
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST);
        baseMapper.update(new SysDepartment(), Wrappers.<SysDepartment>lambdaUpdate()
                .set(SysDepartment::getName, department.getName())
                .set(SysDepartment::getDeptSort, department.getDeptSort())
                .eq(SysDepartment::getId, department.getId())
        );
    }

    @Override
    public void enableDepartment(Long id, Integer enable) {
        SysDepartment dbDepartment = baseMapper.selectById(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST);
        baseMapper.update(new SysDepartment(), Wrappers.<SysDepartment>lambdaUpdate()
                .set(SysDepartment::getEnabled, enable)
                .eq(SysDepartment::getId, id)
        );
    }

    @Override
    public void updateDeptSort(Long id, Integer deptSort) {
        SysDepartment dbDepartment = baseMapper.selectById(id);
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.DEPARTMENT_NOT_EXIST);
        baseMapper.update(new SysDepartment(), Wrappers.<SysDepartment>lambdaUpdate()
                .set(SysDepartment::getDeptSort, deptSort)
                .eq(SysDepartment::getId, id)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteDepartment(Set<Long> ids) {
        List<Long> pids = new ArrayList<>();
        ids.forEach(id -> {
            SysDepartment dept = baseMapper.selectOne(Wrappers.<SysDepartment>lambdaQuery().select(SysDepartment::getPid).eq(SysDepartment::getId, id));
            if (dept != null) {
                pids.add(dept.getPid());
            }
        });
        int count = baseMapper.deleteBatchIds(ids);
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.DEPARTMENT_DELETE_FAIL);
        pids.forEach(id -> {
            this.updateSubCount(id);
        });
    }

    @Override
    public List<SysDepartment> getDepartSearch(String name) {
        List<SysDepartment> list = baseMapper.selectList(Wrappers.<SysDepartment>lambdaQuery().like(SysDepartment::getName, name));
        Map<Long, SysDepartment> map = new HashMap<>(10);
        List<SysDepartment> datas = new ArrayList<>();
        for (SysDepartment department : list) {
            while (department.getPid() != null && department.getPid() != 0) {
                List<SysDepartment> children = new ArrayList<>();
                children.add(department);
                if (map.containsKey(department.getPid())) {
                    SysDepartment parentDept = map.get(department.getPid());
                    parentDept.getChildren().add(department);
                    break;
                } else {
                    department = baseMapper.selectById(department.getPid());
                    department.setChildren(children);
                    map.put(department.getId(), department);
                }
            }
            boolean result = (department.getPid() == null || department.getPid() == 0) && map.get(department.getId()) == department;
            if (result) {
                datas.add(department);
            }
        }
        return datas;
    }

    @Override
    public List<SysDepartment> getSubDepts(Long pid) {
        if (pid == null || pid == 0) {
            return baseMapper.selectList(Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getEnabled, BasicEnum.YES.getCode()).isNull(SysDepartment::getPid).orderByAsc(SysDepartment::getDeptSort));
        } else {
            return baseMapper.selectList(Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getPid, pid).eq(SysDepartment::getEnabled, BasicEnum.YES.getCode()).orderByAsc(SysDepartment::getDeptSort));
        }
    }

    /**
     * 更新子数目
     *
     * @param pid 父id
     */
    private void updateSubCount(Long pid) {
        long count = baseMapper.selectCount(Wrappers.<SysDepartment>lambdaQuery().eq(SysDepartment::getPid, pid));
        baseMapper.update(null, Wrappers.<SysDepartment>lambdaUpdate().set(SysDepartment::getSubCount, count).eq(SysDepartment::getId, pid));
    }
}
