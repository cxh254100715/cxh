package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.framework.shiro.service.PasswordService;
import com.supervisions.modules.sys.dao.IUserDao;
import com.supervisions.modules.sys.dao.IUserRoleDao;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.mapper.UserRole;
import com.supervisions.modules.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户 业务层处理
 */
@Service("userService")
public class UserServiceImpl implements IUserService
{

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserRoleDao userRoleDao;
    @Autowired
    private PasswordService passwordService;

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * 
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userDao.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByName(String userName)
    {
        return userDao.selectUserByName(userName);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId)
    {
        return userDao.selectUserById(userId);
    }


    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int saveUser(User user)
    {
        int count = 0;
        Long userId = user.getId();
        if (StringUtils.isNotNull(userId))
        {
            user.setUpdateUser(ShiroUtils.getLoginName());
            count = userDao.updateUser(user);
            // 删除用户与角色关联
            userRoleDao.deleteUserRoleByUserId(userId);
            // 新增用户与角色管理
            insertUserRole(user);
        }
        else
        {
            user.randomSalt();
            user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
            user.setCreateUser(ShiroUtils.getLoginName());
            // 新增用户信息
            count = userDao.insertUser(user);
            // 新增用户与角色管理
            insertUserRole(user);
        }
        return count;
    }

    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        return userDao.updateUser(user);
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @Override
    public int updateProfile(User user)
    {
        return userDao.updateUser(user);
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        for (Long roleId : user.getRoleIds())
        {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0)
        {
            userRoleDao.batchUserRole(list);
        }
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param user 用户名
     * @return
     */
    @Override
    public String checkUserNameUnique(User user)
    {
        Long userId = user.getId();
        Integer count = userDao.checkUserNameUnique(user.getLoginName());

        if (count > 0)
        {
            if(StringUtils.isNotNull(userId)){
                User info = userDao.selectUserById(userId);
                if(!info.getLoginName().equals(user.getLoginName())){
                    return UserConstants.NAME_NOT_UNIQUE;
                }
            }else{
                return UserConstants.NAME_NOT_UNIQUE;
            }
        }
        return UserConstants.NAME_UNIQUE;
    }
}
