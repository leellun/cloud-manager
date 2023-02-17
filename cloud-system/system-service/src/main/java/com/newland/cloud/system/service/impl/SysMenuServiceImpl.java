package com.newland.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.cloud.enumeration.BasicEnum;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.model.LoginUser;
import com.newland.cloud.security.utils.SecurityUtil;
import com.newland.cloud.system.entity.SysMenu;
import com.newland.cloud.system.entity.SysRole;
import com.newland.cloud.system.enums.MenuTypeEnum;
import com.newland.cloud.system.enums.UserServiceErrorEnum;
import com.newland.cloud.system.mapper.SysMenuMapper;
import com.newland.cloud.system.mapper.SysRoleMapper;
import com.newland.cloud.system.model.vo.MenuVo;
import com.newland.cloud.system.service.SysMenuService;
import com.newland.cloud.utils.AssertUtil;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.page.PageWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public void addMenu(SysMenu sysMenu) {
        if (sysMenu.getPid() != null) {
            SysMenu parentMenu = baseMapper.selectById(sysMenu.getPid());
            AssertUtil.notNull(parentMenu, UserServiceErrorEnum.MENU_PARENT_NOT_EXIST);
        }
        sysMenu.setSubCount(0);
        baseMapper.insert(sysMenu);
        if (sysMenu.getPid() != null) {
            this.updateSubCount(sysMenu.getPid());
        }
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        SysMenu dbDepartment = baseMapper.selectById(sysMenu.getId());
        AssertUtil.notNull(dbDepartment, UserServiceErrorEnum.MENU_NOT_EXIST);
        baseMapper.update(new SysMenu(), Wrappers.<SysMenu>lambdaUpdate()
                .set(SysMenu::getName, sysMenu.getName())
                .set(SysMenu::getPid, sysMenu.getPid())
                .set(SysMenu::getMenuSort, sysMenu.getMenuSort())
                .set(SysMenu::getType, sysMenu.getType())
                .set(SysMenu::getTitle, sysMenu.getTitle())
                .set(SysMenu::getComponent, sysMenu.getComponent())
                .set(SysMenu::getIcon, sysMenu.getIcon())
                .set(SysMenu::getPath, sysMenu.getPath())
                .set(SysMenu::getTarget, sysMenu.getTarget())
                .set(SysMenu::getKeepAlive, sysMenu.getKeepAlive())
                .set(SysMenu::getHidden, sysMenu.getHidden())
                .set(SysMenu::getPermission, sysMenu.getPermission())
                .eq(SysMenu::getId, sysMenu.getId())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteMenu(Set<Long> ids) {
        List<Long> pids = new ArrayList<>();
        ids.forEach(id -> {
            SysMenu sysMenu = baseMapper.selectOne(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getPid).eq(SysMenu::getId, id));
            if (sysMenu != null) {
                pids.add(sysMenu.getPid());
            }
        });
        int count = baseMapper.deleteBatchIds(getSuperior(ids));
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.MENU_DELETE_FAIL);
        pids.forEach(id -> {
            this.updateSubCount(id);
        });
    }

    private List<Long> getSuperior(Set<Long> ids) {
        List<Long> list = new ArrayList();
        List<SysMenu> menus = baseMapper.selectBatchIds(ids);
        while (menus.size() > 0) {
            List<SysMenu> newMenus = new ArrayList<>();
            List<Long> tempIds = new ArrayList<>();
            for (SysMenu menu : menus) {
                tempIds.add(menu.getId());
            }
            list.addAll(tempIds);
            menus = baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getId).in(SysMenu::getPid, tempIds));
        }
        return list;
    }

    @Override
    public List<String> getPermissions(Long userId) {
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(userId, BasicEnum.YES.getCode());
        List<String> permissions = baseMapper.getPermissions(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        permissions.addAll(roles.stream().map(item -> "ROLE_" + item.getName()).collect(Collectors.toList()));
        return permissions;
    }

    @Override
    public List<SysMenu> getSubMenus(Long pid) {
        if (pid == null || pid == 0) {
            return baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery().isNull(SysMenu::getPid).orderByAsc(SysMenu::getMenuSort));
        } else {
            return baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getPid, pid).orderByAsc(SysMenu::getMenuSort));
        }
    }

    @Override
    public Page<SysMenu> getMenuPage(PageEntity pageEntity) {
        pageEntity.setOrder("menuSort");
        Page<SysMenu> page = PageWrapper.wrapper(pageEntity);
        return baseMapper.selectPage(page, Wrappers.<SysMenu>lambdaQuery().isNull(SysMenu::getPid));
    }

    @Override
    public void updateMenuSort(Long id, Integer menuSort) {
        SysMenu dbMenu = baseMapper.selectById(id);
        AssertUtil.notNull(dbMenu, UserServiceErrorEnum.MENU_NOT_EXIST);
        baseMapper.update(new SysMenu(), Wrappers.<SysMenu>lambdaUpdate()
                .set(SysMenu::getMenuSort, menuSort)
                .eq(SysMenu::getId, id)
        );
    }

    @Override
    public void enableMenu(Long id, Integer enable) {
        SysMenu dbMenu = baseMapper.selectById(id);
        AssertUtil.notNull(dbMenu, UserServiceErrorEnum.MENU_NOT_EXIST);
        baseMapper.update(new SysMenu(), Wrappers.<SysMenu>lambdaUpdate()
                .set(SysMenu::getEnabled, enable)
                .eq(SysMenu::getId, id)
        );
    }

    /**
     * 更新子数目
     *
     * @param id id
     */
    private void updateSubCount(Long id) {
        long count = baseMapper.selectCount(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getPid, id));
        baseMapper.update(null, Wrappers.<SysMenu>lambdaUpdate().set(SysMenu::getSubCount, count).eq(SysMenu::getId, id));
    }

    @Override
    public MenuVo getMenu(Long id) {
        SysMenu dbMenu = baseMapper.selectById(id);
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(dbMenu, menuVo);
        if (dbMenu.getPid() != null) {
            SysMenu parentMenu = baseMapper.selectById(dbMenu.getPid());
            if (parentMenu != null) {
                menuVo.setParentName(parentMenu.getTitle());
            }
        }
        return menuVo;
    }

    @Override
    public List<SysMenu> getUserMenus() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(loginUser.getUserId(), BasicEnum.YES.getCode());
        List<SysMenu> menus = baseMapper.getMenuList(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        Map<Long, SysMenu> menuMap = menus.stream().collect(Collectors.toMap(SysMenu::getId, Function.identity()));
        List<Long> pids = menuMap.values().stream().filter(item -> item.getPid() != null && menuMap.get(item.getPid()) == null).map(SysMenu::getPid).distinct().collect(Collectors.toList());
        menus = menus.stream().filter(item -> item.getType().equals(MenuTypeEnum.MENU.getKey())).collect(Collectors.toList());
        while (pids.size() > 0) {
            List<SysMenu> list = baseMapper.selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getId, pids));
            list.forEach(item->{
                menuMap.put(item.getId(),item);
            });
            menus.addAll(list);
            pids = list.stream().filter(item -> item.getPid() != null && !menuMap.containsKey(item.getPid())).map(SysMenu::getPid).distinct().collect(Collectors.toList());
        }
        return menus;
    }

    @Override
    public List<String> getUserPermissions() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(loginUser.getUserId(), BasicEnum.YES.getCode());
        List<String> menus = baseMapper.getPermissions(roles.stream().map(SysRole::getId).collect(Collectors.toList()));
        return menus;
    }
}
