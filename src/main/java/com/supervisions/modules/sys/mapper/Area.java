package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

/**
 * area
 * @author cxh
 */
public class Area extends PageDomain
{

	/** 名称 */
    private String name;
    /** 父级id */
    private Long parentId;
    /** 父级id */
    private String parentName;
    /** 拼音首字母 */
    private String initials;
    /** 拼音首字母集合 */
    private String pinyin;
    /** 行政级别 */
    private String suffix;
    /** 行政代码 */
    private String code;
    /** 排序 */
    private Integer orderNo;
	/** 备注 */
    private String remark;

    private Boolean _children = true;

    private Long pid;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getInitials()
    {
        return initials;
    }

    public void setInitials(String initials)
    {
        this.initials = initials;
    }

    public String getPinyin()
    {
        return pinyin;
    }

    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
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

    public Boolean get_children()
    {
        return _children;
    }

    public void set_children(Boolean _children)
    {
        this._children = _children;
    }

    public Long getPid()
    {
        pid = parentId;
        return pid;
    }

    public void setPid(Long pid)
    {
        this.pid = pid;
    }
}
