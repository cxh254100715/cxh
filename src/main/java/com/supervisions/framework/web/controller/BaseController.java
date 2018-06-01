package com.supervisions.framework.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.framework.web.page.PageDomain;
import com.supervisions.framework.web.page.PageUtilEntity;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.framework.web.support.TableSupport;
import com.supervisions.modules.sys.mapper.User;

import java.util.List;

/**
 * web层通用数据处理

 */
public class BaseController
{
    /**
     * 获取请求分页数据
     */
    public PageUtilEntity getPageUtilEntity()
    {
        PageUtilEntity pageUtilEntity = TableSupport.buildPageRequest();
        return pageUtilEntity;
    }

    /**
     * 设置请求分页数据
     */
    protected void setPageInfo(Object obj)
    {
        PageDomain page = (PageDomain) obj;
        if (StringUtils.isNotEmpty(page.getPage()) && StringUtils.isNotEmpty(page.getLimit()))
        {
            int pageNum = Integer.valueOf(page.getPage());
            int pageSize = Integer.valueOf(page.getLimit());
            String orderBy = page.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setData(list);
        rspData.setCount(new PageInfo(list).getTotal());
        return rspData;
    }

    public User getUser()
    {
        return ShiroUtils.getUser();
    }
    
    public void setUser(User user)
    {
        ShiroUtils.setUser(user);
    }

    public Long getUserId()
    {
        return getUser().getId();
    }

    public String getLoginName()
    {
        return getUser().getLoginName();
    }
}
