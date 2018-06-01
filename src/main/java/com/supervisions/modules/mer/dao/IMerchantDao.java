package com.supervisions.modules.mer.dao;

import com.supervisions.modules.mer.mapper.Merchant;

import java.util.List;

/**
 * merchant 数据层
 * @author cxh
 */
public interface IMerchantDao
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
     * @param name
     * @return
     */
    public Merchant checkNameUnique(String name);

    /**
     * 新增对象
     * @param merchant
     * @return
     */
    public int insertMerchant(Merchant merchant);

    /**
     * 更新对象
     * @param merchant
     * @return
     */
    public int updateMerchant(Merchant merchant);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteMerchantById(Long id);

    public int selectCountMerchantaddressByParentId(Long parentId);
}
