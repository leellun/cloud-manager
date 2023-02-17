package com.newland.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.cloud.system.entity.SysMenu;
import com.newland.cloud.system.model.vo.MenuVo;
import com.newland.mybatis.page.PageEntity;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    List<String> getPermissions(Long userId);
    /**
     * 获取子菜单那
     * @param id
     * @return
     */
    List<SysMenu> getSubMenus(Long id);
    /**
     * 添加菜单
     * @param sysMenu
     */
    void addMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     * @param sysMenu
     */
    void updateMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     * @param ids
     */
    void deleteMenu(Set<Long> ids);
    /**
     * 排序更改
     * @param id 菜单id
     * @param menuSort 排序
     */
    void updateMenuSort(Long id,Integer menuSort);
    /**
     * 更新状态
     * @param id id
     * @param enable 状态
     */
    void enableMenu(Long id,Integer enable);
    /**
     * 获取菜单详情
     * @param id
     * @return
     */
    MenuVo getMenu(Long id);
    /**
     * 获取分页菜单
     * @return
     */
    Page<SysMenu> getMenuPage(PageEntity pageEntity);
    /**
     * 获取用户菜单
     * @return
     */
    List<SysMenu> getUserMenus();
    /**
     * 获取用户权限
     * @return
     */
    List<String> getUserPermissions();

}
