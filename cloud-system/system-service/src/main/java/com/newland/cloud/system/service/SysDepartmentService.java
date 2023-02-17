package com.newland.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.cloud.system.entity.SysDepartment;
import com.newland.cloud.system.model.dto.DeptQueryDTO;
import com.newland.cloud.system.model.vo.SysDepartmentVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysDepartmentService extends IService<SysDepartment> {

    /**
     * 获取部门
     *
     * @param deptQueryDTO
     * @return
     */
    SysDepartmentVo getDepartment(Long id);
    /**
     * 获取部门列表
     *
     * @param deptQueryDTO
     * @return
     */
    Page<SysDepartment> getDepartments(DeptQueryDTO deptQueryDTO);

    /**
     * 添加部门
     *
     * @param department
     */
    void addDepartment(SysDepartment department);

    /**
     * 更新部门
     *
     * @param department
     */

    void updateDepartment(SysDepartment department);
    /**
     * 更新状态
     * @param id id
     * @param enable 状态
     */
    void enableDepartment(Long id,Integer enable);

    /**
     * 排序更改
     * @param id 部门id
     * @param deptSort 排序
     */
    void updateDeptSort(Long id,Integer deptSort);
    /**
     * 删除部门
     *
     * @param ids
     */
    void deleteDepartment(Set<Long> ids);

    /**
     * 获取同级目录和上级目录
     *
     * @param name
     * @return
     */
    List<SysDepartment> getDepartSearch( String name);

    /**
     * 获取子部门
     * @param pid
     * @return
     */
    List<SysDepartment> getSubDepts(Long pid);
}
