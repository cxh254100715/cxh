package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色对象 sys_menu
 */
public class Menu extends PageDomain
{
    /** 菜单名称 */
    private String name;
    /** 父菜单名称 */
    private String parentName;
    /** 父菜单ID */
    private Long parentId;
    /** 显示顺序 */
    private String orderNo;
    /** 菜单URL */
    private String url;
    /** 类型:0菜单,1功能 */
    private String type;
    /** 权限字符串 */
    private String code;
    /** 菜单图标 */
    private String icon;
    /** 备注 */
    private String remark;
    /** 子菜单 */
    private List<Menu> children = new ArrayList<Menu>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }


}
