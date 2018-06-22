package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.TreeUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IMenuDao;
import com.supervisions.modules.sys.dao.IRoleMenuDao;
import com.supervisions.modules.sys.mapper.Menu;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单 业务层处理
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IRoleMenuDao roleMenuDao;

    /**
     * 根据用户ID查询菜单
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<Menu> selectMenusByUserId(Long userId)
    {
        List<Menu> menus = menuDao.selectMenusByUserId(userId);
        return TreeUtils.getChildPerms(menus, 0);
    }

    @Override
    public List<Menu> selectMenusByAdmin()
    {
        List<Menu> menus = menuDao.selectMenusByAdmin();
        return TreeUtils.getChildPerms(menus, 0);
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
    */
    @Override
    public List<Menu> selectMenuAll()
    {
        return menuDao.selectMenuAll();
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
    */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        Set<String> permsSet = new HashSet<>();
        if(userId==1){
            List<String> permsStr = menuDao.selectAllPerms();
            for(String str : permsStr){
                if(StringUtils.isNotEmpty(str)){
                    permsSet.addAll(Arrays.asList(str));
                }
            }
        }else{
            List<String> perms = menuDao.selectPermsByUserId(userId);
            for (String perm : perms)
            {
                if (StringUtils.isNotEmpty(perm))
                {
                    permsSet.addAll(Arrays.asList(perm.trim().split(",")));
                }
            }
        }
        return permsSet;
    }

    /**
     * 根据角色ID查询菜单
     * 
     * @param role 角色对象
     * @return 菜单列表
    */
    @Override
    public List<Map<String, Object>> roleMenuTreeData(Role role)
    {
        Long roleId = role.getId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Menu> menuList = menuDao.selectMenuAll();
        if (StringUtils.isNotNull(roleId))
        {
            List<String> roleMenuList = menuDao.selectMenuTree(roleId);
            trees = getTrees(menuList, true, roleMenuList, true);
        }
        else
        {
            trees = getTrees(menuList, false, null, true);
        }
        return trees;
    }

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
    */
    @Override
    public List<Map<String, Object>> menuTreeData()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Menu> menuList = menuDao.selectMenuTreeAll();
        trees = getTrees(menuList, false, null, false);
        return trees;
    }

    /**
     * 查询系统所有权限
     * 
     * @return 权限列表
    */
    @Override
    public LinkedHashMap<String, String> selectPermsAll()
    {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<Menu> permissions = menuDao.selectMenuAll();
        if (StringUtils.isNotEmpty(permissions))
        {
            for (Menu menu : permissions)
            {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getCode()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList 菜单列表
     * @param isCheck 是否需要选中
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return
    */
    public List<Map<String, Object>> getTrees(List<Menu> menuList, boolean isCheck, List<String> roleMenuList,
            boolean permsFlag)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Menu menu : menuList)
        {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", menu.getId());
            deptMap.put("pId", menu.getParentId());
            deptMap.put("name", transMenuName(menu, roleMenuList, permsFlag));
            if (isCheck)
            {
                deptMap.put("checked", roleMenuList.contains(menu.getId() + menu.getCode()));
            }
            else
            {
                deptMap.put("checked", false);
            }
            trees.add(deptMap);
        }
        return trees;
    }

    public String transMenuName(Menu menu, List<String> roleMenuList, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getName());
        if (permsFlag)
        {
            if(StringUtils.isNotEmpty(menu.getCode())){
                sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getCode() + "</font>");
            }
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
    */
    @Override
    public int deleteMenuById(Long menuId)
    {
        return menuDao.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
    */
    @Override
    public Menu selectMenuById(Long menuId)
    {
        return menuDao.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     * 
     * @param parentId 菜单ID
     * @return 结果
    */
    @Override
    public int selectCountMenuByParentId(Long parentId)
    {
        return menuDao.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
    */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId)
    {
        return roleMenuDao.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 保存菜单信息
     * 
     * @param menu 菜单信息
     * @return 结果
    */
    @Override
    public int saveMenu(Menu menu)
    {
        Long menuId = menu.getId();
        if(menu.getParentId()==null){
            menu.setParentId(0l);
        }
        if (StringUtils.isNotNull(menuId))
        {
            menu.setUpdateUser(ShiroUtils.getLoginName());
            menu.setUpdateTime(new Date());
            return menuDao.updateMenu(menu);
        }
        else
        {
            menu.setCreateUser(ShiroUtils.getLoginName());
            menu.setCreateTime(new Date());
            return menuDao.insertMenu(menu);
        }
    }

    /**
     * 校验菜单名称是否唯一
     * 
     * @param menu 菜单信息
     * @return 结果
    */
    @Override
    public String checkMenuNameUnique(Menu menu)
    {
        Long menuId = menu.getId();
        Menu info = menuDao.checkMenuNameUnique(menu.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(menuId))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

}
