package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IRoleDao;
import com.supervisions.modules.sys.dao.IRoleMenuDao;
import com.supervisions.modules.sys.dao.IUserRoleDao;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.mapper.RoleMenu;
import com.supervisions.modules.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 角色 业务层处理
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService
{

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Autowired
    private IUserRoleDao userRoleDao;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(Role role)
    {
        return roleDao.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<Role> perms = roleDao.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms)
        {
            if (StringUtils.isNotNull(perms))
            {
                permsSet.addAll(Arrays.asList(perm.getCode().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByUserId(Long userId)
    {
        List<Role> userRoles = roleDao.selectRolesByUserId(userId);
        return userRoles;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesAll()
    {
        return roleDao.selectRolesAll();
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(Long roleId)
    {
        return roleDao.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        roleMenuDao.deleteRoleMenuByRoleId(roleId);
        return roleDao.deleteRoleById(roleId);
    }

    /**
     * 保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int saveRole(Role role)
    {
        Long roleId = role.getId();
        if (StringUtils.isNotNull(roleId))
        {
            role.setUpdateUser(ShiroUtils.getLoginName());
            // 修改角色信息
            return roleDao.updateRole(role);
        }
        else
        {
            role.setCreateUser(ShiroUtils.getLoginName());
            // 新增角色信息
            return roleDao.insertRole(role);
        }
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role 角色对象
     */
    @Override
    public int insertRoleMenu(Role role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<RoleMenu>();

        if(StringUtils.isNotNull(role.getId())){
            role.setUpdateUser(ShiroUtils.getLoginName());
            // 修改角色信息
            roleDao.updateRole(role);
            // 删除角色与菜单关联
            roleMenuDao.deleteRoleMenuByRoleId(role.getId());
        }

        for (Long menuId : role.getMenuIds())
        {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuDao.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role)
    {
        Long roleId = role.getId();
        Role info = roleDao.checkRoleNameUnique(role.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(roleId))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int selectCountUserRoleByRoleId(Long roleId)
    {
        return userRoleDao.selectCountUserRoleByRoleId(roleId);
    }

}
