package com.supervisions.modules.ten.mapper;

import com.supervisions.framework.web.page.PageDomain;
import com.supervisions.framework.web.page.PageDomain1;

/**
 * TenHeadimgurl
 * @author cxh
 */
public class TenPicture extends PageDomain
{
    /** 排序 */
    private Integer orderNo;
    /** url */
    private String url;
    /** 类型 0:app */
    private Integer type;

    public Integer getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }
}
