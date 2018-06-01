package com.supervisions.modules.mer.mapper;

import com.supervisions.framework.web.page.PageDomain;

/**
 * merchantaddress
 * @author cxh
 */
public class Merchantaddress extends PageDomain
{

	/** 名称 */
    private String name;
	/** 客户id */
    private Long merchantId;
    /** 客户名 */
    private String merchantName;
	/** 区域id（区） */
    private Long areaId;
	/** 详细地址 */
    private String address;
	/** 联系人 */
    private String contact;
	/** 联系电话 */
    private String mobile;
    /** 区域名称 */
    private String areaName;

    public String getName(){
       return name ;
    }
	
    public void setName(String name){
       this.name = name;
    }
    public Long getMerchantId(){
       return merchantId ;
    }
	
    public void setMerchantId(Long merchantId){
       this.merchantId = merchantId;
    }

    public String getMerchantName()
    {
        return merchantName;
    }

    public void setMerchantName(String merchantName)
    {
        this.merchantName = merchantName;
    }

    public Long getAreaId(){
       return areaId ;
    }
	
    public void setAreaId(Long areaId){
       this.areaId = areaId;
    }
    public String getAddress(){
       return address ;
    }
	
    public void setAddress(String address){
       this.address = address;
    }
    public String getContact(){
       return contact ;
    }
	
    public void setContact(String contact){
       this.contact = contact;
    }
    public String getMobile(){
       return mobile ;
    }
	
    public void setMobile(String mobile){
       this.mobile = mobile;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

}
