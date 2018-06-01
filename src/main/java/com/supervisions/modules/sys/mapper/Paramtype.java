package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

/**
 * paramtype
 * @author cxh
 */
public class Paramtype extends PageDomain
{

	/** 名称 */
    private String name;
	/** 编码 */
    private String code;
	/** 类型 0:box 1:tv 2:camera */
    private Integer type;
	/** 备注 */
    private String remark;
    /** 排序 */
    private Integer orderNo;

    public String getName(){
       return name ;
    }
	
    public void setName(String name){
       this.name = name;
    }
    public String getCode(){
       return code ;
    }
	
    public void setCode(String code){
       this.code = code;
    }
    public Integer getType(){
       return type ;
    }
	
    public void setType(Integer type){
       this.type = type;
    }
    public String getRemark(){
       return remark ;
    }
	
    public void setRemark(String remark){
       this.remark = remark;
    }

    public Integer getOrderNo(){
        return orderNo ;
    }

    public void setOrderNo(Integer orderNo){
        this.orderNo = orderNo;
    }

}
