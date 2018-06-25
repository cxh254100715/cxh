package com.supervisions.modules.sys.dao;

import com.supervisions.modules.sys.mapper.UserRole;

import java.util.List;

/**
 * 用户表 数据层
 */
public interface IUserRoleDao
{

    /**
     * 通过用户ID删除用户和角色关联
     * 
     * @param id 用户ID
     * @return 结果
     */
    public int deleteUserRoleByUserId(Long id);

    /**
     * 批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int selectCountUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     * 
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<UserRole> userRoleList);

}
