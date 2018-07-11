package com.supervisions.modules.dev.mapper;

import com.supervisions.framework.web.page.PageDomain;

/**
 * device
 * @author cxh
 */
public class Device extends PageDomain
{

	/** 名称 */
    private String name;
	/** 类型 0:box 1:tv 2:camera */
    private Integer type;
	/** 型号 */
    private String model;
	/** 参数(param_data外键，逗号隔开) */
    private String param;
	/** 备注 */
    private String remark;

    public String getName(){
       return name ;
    }
	
    public void setName(String name){
       this.name = name;
    }
    public Integer getType(){
       return type ;
    }
	
    public void setType(Integer type){
       this.type = type;
    }
    public String getModel(){
       return model ;
    }
	
    public void setModel(String model){
       this.model = model;
    }
    public String getParam(){
       return param ;
    }
	
    public void setParam(String param){
       this.param = param;
    }
    public String getRemark(){
       return remark ;
    }
	
    public void setRemark(String remark){
       this.remark = remark;
    }

}
