package com.supervisions.modules.mer.service;

import com.supervisions.modules.mer.mapper.Merchant;

import java.util.List;

/**
 * merchant 业务层
 */
public interface IMerchantService
{

    /**
     * 根据条件查询分页数据
     * @param merchant
     * @return
     */
    public List<Merchant> selectMerchantList(Merchant merchant);

    /**
     * 查询所有客户
     */
    public List<Merchant> selectAllMerchants();

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Merchant selectMerchantById(Long id);

    /**
     * 检查名称是否唯一
     * @param merchant
     * @return
     */
    public String checkNameUnique(Merchant merchant);

    /**
     * 保存对象
     * @param merchant
     * @return
     */
    public int saveMerchant(Merchant merchant);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteMerchantById(Long id);

    public int selectCountMerchantaddressByParentId(Long parentId);
}
