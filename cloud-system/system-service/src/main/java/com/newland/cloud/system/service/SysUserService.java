package com.newland.cloud.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.cloud.model.LoginUser;
import com.newland.cloud.system.dto.LoginDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.cloud.system.entity.SysUser;
import com.newland.cloud.system.model.dto.*;
import com.newland.cloud.system.model.vo.SysUserItemVo;
import com.newland.cloud.system.model.vo.SysUserVo;

import java.util.Set;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 登录
     * @param loginDTO
     * @return
     */
    LoginUser login(LoginDTO loginDTO);

    /**
     * 分页查询
     * @param userQueryDTO
     * @return
     */
    Page<SysUserVo> getUsers(UserQueryDTO userQueryDTO);
    /**
     * 获取用户
     * @param userId 用户id
     * @return
     */
    SysUserItemVo getUser(Long userId);

    /**
     * 添加用户
     * @param sysUserDto
     */
    void addUser(SysUserDto sysUserDto);

    /**
     * 更新用户
     * @param sysUserDto
     */
    void updateUser(SysUserDto sysUserDto);
    /**
     * 更新用户状态
     * @param id 用户id
     * @param enable 状态
     */
    void enableUser(Long id,Integer enable);

    /**
     * 个人中心修改
     * @param userCenterDTO
     */
    void updateCenter(UserCenterDTO userCenterDTO);

    /**
     * 删除用户
     * @param ids
     */
    void deleteUser(Set<Long> ids);

    /**
     * 更新密码
     * @param userPassVO
     */
    void updatePass(UserPassVO userPassVO);

    /**
     * 重置密码
     */
    void resetPass(Long id);

    /**
     * 更新头像
     * @param avatar
     */
    void updateAvatar(String avatar);
}
