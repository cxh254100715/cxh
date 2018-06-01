package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

/**
 * paramdata
 * @author cxh
 */
public class Paramdata extends PageDomain
{

	/** 名称 */
    private String name;
	/** param_data表id */
    private String paramId;
    /** param_data表name */
    private String paramName;
	/** 值 */
    private String value;
	/** 排序 */
    private Integer orderNo;
	/** 备注 */
    private String remark;

    public String getName(){
       return name ;
    }
	
    public void setName(String name){
       this.name = name;
    }
    public String getParamId(){
       return paramId ;
    }
	
    public void setParamId(String paramId){
       this.paramId = paramId;
    }

    public String getParamName()
    {
        return paramName;
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }

    public String getValue(){
       return value ;
    }
	
    public void setValue(String value){
       this.value = value;
    }
    public Integer getOrderNo(){
       return orderNo ;
    }
	
    public void setOrderNo(Integer orderNo){
       this.orderNo = orderNo;
    }
    public String getRemark(){
       return remark ;
    }
	
    public void setRemark(String remark){
       this.remark = remark;
    }

}
