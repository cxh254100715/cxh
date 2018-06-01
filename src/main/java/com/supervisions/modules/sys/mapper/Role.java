package com.supervisions.modules.sys.mapper;


import com.supervisions.framework.web.page.PageDomain;

import java.util.Arrays;
import java.util.Date;

/**
 * 角色对象 sys_role
 */
public class Role extends PageDomain
{
    /** 角色名 */
    private String name;
    /** 角色权限 */
    private String code;
    /** 角色排序 */
    private Integer orderNo;
    /** 备注 */
    private String remark;
    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;
    /** 菜单组 */
    private Long[] menuIds;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public Long[] getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds)
    {
        this.menuIds = menuIds;
    }
}
