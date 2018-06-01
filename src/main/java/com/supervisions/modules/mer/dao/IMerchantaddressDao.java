package com.supervisions.modules.mer.dao;

import com.supervisions.framework.web.dao.IBaseDao;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.mer.mapper.Merchantaddress;

import java.util.List;

/**
 * merchantaddress 数据层
 * @author cxh
 */
public interface IMerchantaddressDao extends IBaseDao<Merchantaddress>
{
    /**
     * 根据条件查询分页数据
     * @param merchantaddress
     * @return
     */
    //public List<Merchantaddress> selectMerchantaddressList(Merchantaddress merchantaddress);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    //public Merchantaddress selectMerchantaddressById(Long id);

    /**
     * 检查名称是否唯一
     * @param name
     * @return
     */
    //public Merchantaddress checkNameUnique(String name);

    /**
     * 新增对象
     * @param merchantaddress
     * @return
     */
    //public int insertMerchantaddress(Merchantaddress merchantaddress);

    /**
     * 更新对象
     * @param merchantaddress
     * @return
     */
    //public int updateMerchantaddress(Merchantaddress merchantaddress);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    //public int deleteMerchantaddressById(Long id);

    /**
     * 根据客户id获取地址下拉数据
     * @param parentId
     * @return
     */
    public List<Merchantaddress> selectMerchantaddressByParentId(Long parentId);

    public int selectCountDeviceinfoByMerchantaddressId(Long id);
}
