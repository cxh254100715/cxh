package com.supervisions.modules.sys.service;

import com.supervisions.modules.sys.mapper.User;

import java.util.List;

/**
 * 用户 业务层
 */
public interface IUserService
{

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByName(String userName);

    /**
     * 通过用户ID查询用户
     * 
     * @param id 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Long id);

    /**
     * 保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int saveUser(User user);

    /**
     * 修改用户密码信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(User user);

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    public int updateProfile(User user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param user
     * @return 结果
     */
    public String checkUserNameUnique(User user);

}
