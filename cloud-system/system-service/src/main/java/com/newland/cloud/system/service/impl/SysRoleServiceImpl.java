package com.newland.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.cloud.constant.Constant;
import com.newland.cloud.enumeration.BasicEnum;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.system.entity.SysMenu;
import com.newland.cloud.system.entity.SysRole;
import com.newland.cloud.system.entity.SysRoleMenu;
import com.newland.cloud.system.entity.SysUserRole;
import com.newland.cloud.system.enums.UserServiceErrorEnum;
import com.newland.cloud.system.mapper.*;
import com.newland.cloud.system.model.dto.RoleQueryDTO;
import com.newland.cloud.system.service.SysRoleMenuService;
import com.newland.cloud.system.service.SysRoleService;
import com.newland.cloud.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.redis.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysRole> getAllRoles() {
        return baseMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getEnabled, BasicEnum.YES.getCode()).orderByAsc(SysRole::getLevel));
    }

    @Override
    public Page<SysRole> getRolePage(RoleQueryDTO roleQueryDTO) {
        Page<SysRole> page = PageWrapper.wrapper(roleQueryDTO);
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(roleQueryDTO.getName())) {
            wrapper.like(SysRole::getName, roleQueryDTO.getName());
        }
        if (roleQueryDTO.getEnabled() != null) {
            wrapper.eq(SysRole::getEnabled, roleQueryDTO.getEnabled());
        }
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public SysRole getRole(Long id) {
        SysRole sysRole = baseMapper.selectById(id);
        AssertUtil.notNull(sysRole, UserServiceErrorEnum.ROLE_NOT_EXIST);
        return sysRole;
    }

    @Override
    public void addRole(SysRole sysRole) {
        SysRole dbRole = baseMapper.selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, sysRole.getName()));
        AssertUtil.isNull(dbRole, UserServiceErrorEnum.ROLE_NAME_EXIST);
        dbRole = baseMapper.selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, sysRole.getCode()));
        AssertUtil.isNull(dbRole, UserServiceErrorEnum.ROLE_CODE_EXIST);
        baseMapper.insert(sysRole);
    }

    @Override
    public void updateRole(SysRole sysRole) {
        SysRole role = baseMapper.selectById(sysRole.getId());
        AssertUtil.notNull(role, UserServiceErrorEnum.ROLE_NOT_EXIST);
        if (!sysRole.getName().equals(role.getName())) {
            SysRole dbRole = baseMapper.selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getName, sysRole.getName()));
            AssertUtil.isNull(dbRole, UserServiceErrorEnum.ROLE_NAME_EXIST);
        }
        if (!sysRole.getCode().equals(role.getCode())) {
            SysRole dbRole = baseMapper.selectOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getCode, sysRole.getCode()));
            AssertUtil.isNull(dbRole, UserServiceErrorEnum.ROLE_CODE_EXIST);
        }
        baseMapper.updateById(sysRole);
    }

    @Override
    public void enableRole(Long id, Integer enable) {
        SysRole role = baseMapper.selectById(id);
        AssertUtil.notNull(role, UserServiceErrorEnum.ROLE_NOT_EXIST);
        baseMapper.update(new SysRole(), Wrappers.<SysRole>lambdaUpdate()
                .set(SysRole::getEnabled, enable)
                .eq(SysRole::getId, id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteRoles(Set<Long> ids) {
        int count = baseMapper.deleteBatchIds(ids);
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.ROLE_DELETE_FAIL);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getRoleId, ids));
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, ids));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void addMenuPermission(Long id, Set<Long> permissions) {
        List<Long> permissionIds = getAllExpandMenuIds(permissions);
        List<Object> menuObjs = sysRoleMenuMapper.selectObjs(Wrappers.<SysRoleMenu>lambdaQuery().select(SysRoleMenu::getMenuId).eq(SysRoleMenu::getRoleId, id));
        List<Long> menuIds = menuObjs.stream().map(item -> (Long) item).collect(Collectors.toList());
        List<Long> extraIds = menuIds.stream().filter(item -> !permissionIds.contains(item)).collect(Collectors.toList());
        permissionIds.removeAll(menuIds);
        if (extraIds.size() > 0) {
            sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id).in(SysRoleMenu::getMenuId, extraIds));
        }
        Set<SysRoleMenu> roleMenus = new HashSet<>();
        for (Long menuId : permissionIds) {
            roleMenus.add(new SysRoleMenu(id, menuId));
            if (roleMenus.size() >= 1000) {
                sysRoleMenuService.saveBatch(roleMenus);
                roleMenus.clear();
            }
        }
        if (roleMenus.size() >= 0) {
            sysRoleMenuService.saveBatch(roleMenus);
        }
        List<Object> uObjs = sysUserRoleMapper.selectObjs(Wrappers.<SysUserRole>lambdaQuery().select(SysUserRole::getUserId).eq(SysUserRole::getRoleId, id));
        List<Long> userIds = uObjs.stream().map(item -> (Long) item).collect(Collectors.toList());
        userIds.forEach(userId -> {
            redisUtil.del(Constant.USER_LOGIN_INFO + userId);
        });
    }

    @Override
    public List<Long> getMenuPermission(Long id) {
        List<Object> menuObjs = sysRoleMenuMapper.selectObjs(Wrappers.<SysRoleMenu>lambdaQuery().select(SysRoleMenu::getMenuId).eq(SysRoleMenu::getRoleId, id));
        List<Long> menuIds = menuObjs.stream().map(item -> (Long) item).collect(Collectors.toList());
        return menuIds;
    }

    /**
     * 获取所有菜单id，包括未展开的
     *
     * @param ids
     * @return
     */
    private List<Long> getAllExpandMenuIds(Collection<Long> ids) {
        List<Long> list = new ArrayList<>();
        List<Long> checkPids=new ArrayList<>(ids);
        while (ids.size() > 0) {
            Set<Long> set = new HashSet<>(ids);
            list.addAll(set);
            List<Object> menuObjs = sysMenuMapper.selectObjs(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getId).in(SysMenu::getPid, ids));
            ids = menuObjs.stream().map(item -> (Long) item).collect(Collectors.toList());
        }
        return list;
    }
}
