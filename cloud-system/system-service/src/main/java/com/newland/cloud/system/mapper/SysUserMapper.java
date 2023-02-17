package com.newland.cloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.cloud.system.entity.SysUser;
import com.newland.cloud.system.model.dto.LevelRoleDTO;
import com.newland.cloud.system.model.dto.UserQueryDTO;
import com.newland.cloud.system.model.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 分页获取用户列表
     * @param wrapper 分页
     * @param dto 查询信息
     * @return 分页列表
     */
    Page<SysUserVo> selectUsersPage(Page<SysUserVo> wrapper,@Param("dto") UserQueryDTO dto);
    /**
     * 获取非指定角色下用户
     *
     * @param wrapper
     * @param levelRoleDTO
     * @return
     */
    Page<SysUser> selectNotByRoleId(Page<Object> wrapper, @Param("dto") LevelRoleDTO levelRoleDTO);

    /**
     * 获取指定角色用户
     * @param wrapper
     * @param levelRoleDTO
     * @return
     */
    Page<SysUser> selectByRoleId(Page<Object> wrapper, @Param("dto") LevelRoleDTO levelRoleDTO);
}
