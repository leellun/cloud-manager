package com.newland.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.cloud.enumeration.BasicEnum;
import com.newland.cloud.exception.BusinessException;
import com.newland.cloud.model.LoginUser;
import com.newland.cloud.security.utils.SecurityUtil;
import com.newland.cloud.system.common.UserConstant;
import com.newland.cloud.system.dto.LoginDTO;
import com.newland.cloud.system.entity.SysDepartment;
import com.newland.cloud.system.entity.SysRole;
import com.newland.cloud.system.entity.SysUser;
import com.newland.cloud.system.entity.SysUserRole;
import com.newland.cloud.system.enums.UserServiceErrorEnum;
import com.newland.cloud.system.mapper.SysDepartmentMapper;
import com.newland.cloud.system.mapper.SysRoleMapper;
import com.newland.cloud.system.mapper.SysUserMapper;
import com.newland.cloud.system.mapper.SysUserRoleMapper;
import com.newland.cloud.system.model.dto.*;
import com.newland.cloud.system.model.vo.SysUserItemVo;
import com.newland.cloud.system.model.vo.SysUserVo;
import com.newland.cloud.system.service.SysMenuService;
import com.newland.cloud.system.service.SysUserService;
import com.newland.cloud.utils.AesUtils;
import com.newland.cloud.utils.AssertUtil;
import com.newland.cloud.utils.MD5;
import com.newland.mybatis.page.PageWrapper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public LoginUser login(LoginDTO loginDTO) {
        String password = AesUtils.decrypt(loginDTO.getPassword());
        String md5Password = MD5.encrypt(password);
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, loginDTO.getUsername()));
        AssertUtil.notNull(sysUser, UserServiceErrorEnum.USER_NOT_EXIST);
        AssertUtil.isTrue(md5Password.endsWith(sysUser.getPassword()), UserServiceErrorEnum.USER_PASSWORD_ERROR);
        baseMapper.update(null, Wrappers.<SysUser>lambdaUpdate().set(SysUser::getLastLoginTime, LocalDateTime.now()).eq(SysUser::getId, sysUser.getId()));
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(sysUser.getUsername());
        loginUser.setMobile(sysUser.getPhone());
        loginUser.setDepartmentId(sysUser.getDeptId());
        loginUser.setUserId(sysUser.getId());
        loginUser.setAuthorities(sysMenuService.getPermissions(sysUser.getId()));
        return loginUser;
    }

    @Override
    public Page<SysUserVo> getUsers(UserQueryDTO userQueryDTO) {
        Page<SysUserVo> page = PageWrapper.wrapper(userQueryDTO);
        Page<SysUserVo> result = baseMapper.selectUsersPage(page, userQueryDTO);
        result.getRecords().forEach(item -> {
            List<SysRole> roles = sysRoleMapper.getRoleWithIdByUserId(item.getId(), null);
            item.setRoleNames(roles.stream().map(SysRole::getName).collect(Collectors.toList()));
        });
        return result;
    }

    @Override
    public SysUserItemVo getUser(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        AssertUtil.notNull(sysUser, UserServiceErrorEnum.USER_NOT_EXIST);
        SysUserItemVo sysUserVo = new SysUserItemVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        sysUserVo.setRoleIds(sysUserRoles.stream().map(item -> String.valueOf(item.getRoleId())).collect(Collectors.toList()));
        if (sysUser.getDeptId() != null) {
            SysDepartment department = sysDepartmentMapper.selectById(sysUser.getDeptId());
            sysUserVo.setDeptName(department.getName());
        }

        return sysUserVo;
    }

    @Override
    @Transactional(noRollbackFor = {BusinessException.class}, rollbackFor = {Exception.class})
    public void addUser(SysUserDto sysUserDto) {
        AssertUtil.isTrue(sysUserDto.getRoleIds() != null && sysUserDto.getRoleIds().size() > 0, UserServiceErrorEnum.ROLE_NOT_SELECT);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDto, sysUser);
        RLock lock = getUserLock(sysUser);
        lock.lock();
        try {
            SysUser dbUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getUsername, sysUser.getUsername()));
            AssertUtil.isNull(dbUser, UserServiceErrorEnum.USER_EXIST);
            dbUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getPhone, sysUser.getPhone()));
            AssertUtil.isNull(dbUser, UserServiceErrorEnum.USER_PHONE_EXIST);
            dbUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getEmail, sysUser.getEmail()));
            AssertUtil.isNull(dbUser, UserServiceErrorEnum.USER_EMAIL_EXIST);
            sysUser.setPassword(MD5.encrypt(UserConstant.DEFAULT_PASSWORD));
            sysUser.setEnabled(BasicEnum.YES.getCode());
            sysUser.setMustResetPwd(BasicEnum.YES.getCode());
            sysUser.setPwdFailsCount(0);

            List<SysRole> roleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().select(SysRole::getId).in(SysRole::getId, sysUserDto.getRoleIds()));
            AssertUtil.isTrue(roleList.size() > 0, UserServiceErrorEnum.ROLE_NOT_SELECT);

            baseMapper.insert(sysUser);

            roleList.forEach(item -> sysUserRoleMapper.insert(new SysUserRole(sysUser.getId(), item.getId())));
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(noRollbackFor = {BusinessException.class}, rollbackFor = {Exception.class})
    public void updateUser(SysUserDto sysUserDto) {
        AssertUtil.isTrue(sysUserDto.getRoleIds() != null && sysUserDto.getRoleIds().size() > 0, UserServiceErrorEnum.ROLE_NOT_SELECT);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDto, sysUser);
        SysUser dbUser = baseMapper.selectById(sysUser.getId());
        AssertUtil.notNull(dbUser, UserServiceErrorEnum.USER_NOT_EXIST);
        RLock lock = getUserLock(sysUser);
        lock.lock();
        try {
            if (!StringUtils.equals(dbUser.getUsername(), sysUser.getUsername())) {
                SysUser dbData = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getUsername, sysUser.getUsername()));
                AssertUtil.isNull(dbData, UserServiceErrorEnum.USER_EXIST);
            }
            if (!StringUtils.equals(dbUser.getPhone(), sysUser.getPhone())) {
                if (StringUtils.isNotEmpty(sysUser.getPhone())) {
                    SysUser dbData = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getPhone, sysUser.getPhone()));
                    AssertUtil.isNull(dbData, UserServiceErrorEnum.USER_PHONE_EXIST);
                }
            }
            if (!StringUtils.equals(dbUser.getEmail(), sysUser.getEmail())) {
                if (StringUtils.isNotEmpty(sysUser.getEmail())) {
                    SysUser dbData = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getId).eq(SysUser::getEmail, sysUser.getEmail()));
                    AssertUtil.isNull(dbData, UserServiceErrorEnum.USER_EMAIL_EXIST);
                }
            }
            sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getId()));

            List<SysRole> roleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().select(SysRole::getId).in(SysRole::getId, sysUserDto.getRoleIds()));
            AssertUtil.isTrue(roleList.size() > 0, UserServiceErrorEnum.ROLE_NOT_SELECT);

            baseMapper.update(new SysUser(), Wrappers.<SysUser>lambdaUpdate()
                    .set(SysUser::getRealName, sysUser.getRealName())
                    .set(SysUser::getNickName, sysUser.getNickName())
                    .set(SysUser::getPhone, sysUser.getPhone())
                    .set(SysUser::getEmail, sysUser.getEmail())
                    .set(SysUser::getGender, sysUser.getGender())
                    .set(SysUser::getDeptId, sysUser.getDeptId())
                    .set(SysUser::getJobId, sysUser.getJobId())
            );

            roleList.forEach(item -> sysUserRoleMapper.insert(new SysUserRole(sysUser.getId(), item.getId())));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void enableUser(Long id, Integer enable) {
        SysUser dbUser = baseMapper.selectById(id);
        AssertUtil.notNull(dbUser, UserServiceErrorEnum.USER_NOT_EXIST);
        baseMapper.update(new SysUser(), Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getEnabled, enable)
                .eq(SysUser::getId, id)
        );
    }

    @Override
    public void updateCenter(UserCenterDTO userCenterDTO) {
        SysUser dbUser = baseMapper.selectById(userCenterDTO.getId());
        AssertUtil.notNull(dbUser, UserServiceErrorEnum.USER_NOT_EXIST);
        baseMapper.update(new SysUser(), Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getNickName, userCenterDTO.getNickName())
                .set(SysUser::getPhone, userCenterDTO.getPhone())
                .set(SysUser::getGender, userCenterDTO.getGender())
                .eq(SysUser::getId, userCenterDTO.getId())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = BusinessException.class)
    public void deleteUser(Set<Long> ids) {
        int count = baseMapper.delete(Wrappers.<SysUser>lambdaQuery().in(SysUser::getId, ids).eq(SysUser::getCanDeleted, BasicEnum.YES.getCode()));
        AssertUtil.isTrue(count > 0, UserServiceErrorEnum.USER_DELETE_FAIL);
        //删除用户关联角色
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, ids));
    }

    @Override
    public void updatePass(UserPassVO userPassVO) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        SysUser dbUser = baseMapper.selectById(loginUser.getUserId());
        String oldMd5Password = MD5.encrypt(AesUtils.decrypt(userPassVO.getOldPass()));
        AssertUtil.isNull(dbUser.getPassword().equals(oldMd5Password), UserServiceErrorEnum.USER_OLD_PASSWORD_ERROR);
        String md5Password = MD5.encrypt(AesUtils.decrypt(userPassVO.getNewPass()));
        baseMapper.update(null, Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, md5Password)
                .eq(SysUser::getId, loginUser.getUserId())
        );
    }

    @Override
    public void resetPass(Long id) {
        SysUser dbUser = baseMapper.selectById(id);
        AssertUtil.notNull(dbUser, UserServiceErrorEnum.USER_NOT_EXIST);
        String md5Password = MD5.encrypt(UserConstant.DEFAULT_PASSWORD);
        baseMapper.update(new SysUser(), Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, md5Password)
                .set(SysUser::getPwdResetTime, LocalDateTime.now())
                .eq(SysUser::getId, dbUser.getId())
        );
    }

    @Override
    public void updateAvatar(String avatar) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        SysUser dbUser = baseMapper.selectById(loginUser.getUserId());
        AssertUtil.notNull(dbUser, UserServiceErrorEnum.USER_NOT_EXIST);
        baseMapper.update(null, Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getAvatar, avatar)
                .eq(SysUser::getId, dbUser.getId())
        );
    }

    /**
     * 获取复合锁
     *
     * @param sysUser 用户
     * @return 复合锁
     */
    private RLock getUserLock(SysUser sysUser) {
        List<RLock> rLocks = new ArrayList<>();
        RLock lock = redissonClient.getLock(UserConstant.LOCK_USER_OPERATION_USERNAME_PREFIX + sysUser.getUsername());
        rLocks.add(lock);
        if (StringUtils.isNotEmpty(sysUser.getPhone())) {
            lock = redissonClient.getLock(UserConstant.LOCK_USER_OPERATION_PHONE_PREFIX + sysUser.getPhone());
            rLocks.add(lock);
        }
        if (StringUtils.isNotEmpty(sysUser.getEmail())) {
            lock = redissonClient.getLock(UserConstant.LOCK_USER_OPERATION_EMAIL_PREFIX + sysUser.getEmail());
            rLocks.add(lock);
        }
        return redissonClient.getMultiLock(rLocks.toArray(RLock[]::new));
    }
}
