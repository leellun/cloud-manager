package com.newland.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.cloud.system.entity.SysRole;
import com.newland.cloud.system.model.dto.RoleQueryDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 查询所有角色
     *
     * @return
     */
    List<SysRole> getAllRoles();
    /**
     * 分页查询角色
     *
     * @param roleQueryDTO
     * @return
     */
    Page<SysRole> getRolePage(RoleQueryDTO roleQueryDTO);
    /**
     * 获取单个角色
     *
     * @param id 角色id
     * @return
     */
    SysRole getRole(Long id);

    /**
     * 添加角色
     *
     * @param sysRole
     */
    void addRole(SysRole sysRole);

    /**
     * 更新角色
     *
     * @param sysRole
     */
    void updateRole(SysRole sysRole);
    /**
     * 更新状态
     * @param id id
     * @param enable 状态
     */
    void enableRole(Long id,Integer enable);
    /**
     * 删除角色
     *
     * @param ids
     */
    void deleteRoles(Set<Long> ids);

    /**
     * 添加权限
     * @param id 角色id
     * @param permissions 权限菜单id列表
     */
    void addMenuPermission(Long id, Set<Long> permissions);

    /**
     * 获取权限
     * @param id 角色id
     * @return
     */
    List<Long> getMenuPermission(Long id);
}
