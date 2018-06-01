package com.supervisions.modules.mer.service;

import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.mer.mapper.Merchantaddress;

import java.util.List;

/**
 * merchantaddress 业务层
 */
public interface IMerchantaddressService
{

    /**
     * 根据条件查询分页数据
     * @param merchantaddress
     * @return
     */
    public List<Merchantaddress> selectMerchantaddressList(Merchantaddress merchantaddress);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Merchantaddress selectMerchantaddressById(Long id);

    /**
     * 检查名称是否唯一
     * @param merchantaddress
     * @return
     */
    public String checkNameUnique(Merchantaddress merchantaddress);

    /**
     * 保存对象
     * @param merchantaddress
     * @return
     */
    public int saveMerchantaddress(Merchantaddress merchantaddress);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteMerchantaddressById(Long id);

    /**
     * 根据客户id获取地址下拉数据
     * @param parentId
     * @return
     */
    public List<Merchantaddress> selectMerchantaddressByParentId(Long parentId);

    public int selectCountDeviceinfoByMerchantaddressId(Long id);
}
