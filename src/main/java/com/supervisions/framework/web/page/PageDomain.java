package com.supervisions.framework.web.page;

import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.web.domain.DataEntity;

/**
 * 分页数据
 */
public class PageDomain extends DataEntity
{
    /** 当前记录起始索引 */
    private String page;
    /** 每页显示记录数 */
    private String limit;
    /** 排序列 */
    private String sort;
    /** 排序的方向 "desc" 或者 "asc". */
    private String order;
    /** 搜索值 */
    private String searchValue;


    public String getOrderBy()
    {
        if (StringUtils.isEmpty(sort))
        {
            return "";
        }
        return StringUtils.propertyToField(sort) + " " + order;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public String getLimit()
    {
        return limit;
    }

    public void setLimit(String limit)
    {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

}
