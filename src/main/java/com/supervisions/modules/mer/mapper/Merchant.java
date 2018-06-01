package com.supervisions.modules.mer.mapper;

import com.supervisions.framework.web.page.PageDomain;
import com.supervisions.modules.sys.mapper.Area;

/**
 * merchant
 * @author cxh
 */
public class Merchant extends PageDomain
{

	/** 名称 */
    private String name;
	/** 联系人 */
    private String contact;
	/** 手机 */
    private String mobile;
	/** 电话1 */
    private String phone1;
	/** 电话2 */
    private String phone2;
	/** 区域id（区） */
    private Long areaId;
	/** 详细地址 */
    private String address;
	/** 短简介 */
    private String sIntro;
	/** 长简介 */
    private String lIntro;
	/** 商户logo */
    private String logo;
	/** 经纬度 */
    private String lonLat;
	/** 审核状态 0:未审核 1:已审核 */
    private Integer auditStatus;
    /** 区域名称 */
    private String areaName;

    public String getName(){
       return name ;
    }
	
    public void setName(String name){
       this.name = name;
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
    public String getPhone1(){
       return phone1 ;
    }
	
    public void setPhone1(String phone1){
       this.phone1 = phone1;
    }
    public String getPhone2(){
       return phone2 ;
    }
	
    public void setPhone2(String phone2){
       this.phone2 = phone2;
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
    public String getLogo(){
       return logo ;
    }
	
    public void setLogo(String logo){
       this.logo = logo;
    }
    public String getLonLat(){
       return lonLat ;
    }
	
    public void setLonLat(String lonLat){
       this.lonLat = lonLat;
    }
    public Integer getAuditStatus(){
       return auditStatus ;
    }
	
    public void setAuditStatus(Integer auditStatus){
       this.auditStatus = auditStatus;
    }

    public String getsIntro()
    {
        return sIntro;
    }

    public void setsIntro(String sIntro)
    {
        this.sIntro = sIntro;
    }

    public String getlIntro()
    {
        return lIntro;
    }

    public void setlIntro(String lIntro)
    {
        this.lIntro = lIntro;
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
