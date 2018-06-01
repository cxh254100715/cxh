package com.supervisions.modules.mer.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.mer.dao.IMerchantaddressDao;
import com.supervisions.modules.mer.mapper.Merchantaddress;
import com.supervisions.modules.mer.service.IMerchantaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * merchantaddress 业务层处理
 */
@Service("merchantaddressService")
public class MerchantaddressServiceImpl implements IMerchantaddressService
{

    @Autowired
    private IMerchantaddressDao merchantaddressDao;

    @Override
    public List<Merchantaddress> selectMerchantaddressList(Merchantaddress merchantaddress)
    {
        return merchantaddressDao.selectList(merchantaddress);
    }

    @Override
    public Merchantaddress selectMerchantaddressById(Long id)
    {
        return merchantaddressDao.selectById(id);
    }

    @Override
    public String checkNameUnique(Merchantaddress merchantaddress)
    {
        Long id = merchantaddress.getId();
        Merchantaddress info = merchantaddressDao.checkNameUnique(merchantaddress.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveMerchantaddress(Merchantaddress merchantaddress)
    {
        int count = 0;
        Long id = merchantaddress.getId();
        if (StringUtils.isNotNull(id))
        {
            merchantaddress.setUpdateUser(ShiroUtils.getLoginName());
            merchantaddress.setUpdateTime(new Date());
            count = merchantaddressDao.update(merchantaddress);
        }
        else
        {
            merchantaddress.setCreateUser(ShiroUtils.getLoginName());
            merchantaddress.setCreateTime(new Date());
            count = merchantaddressDao.insert(merchantaddress);
        }
        return count;
    }

    @Override
    public int deleteMerchantaddressById(Long id)
    {
        return merchantaddressDao.deleteById(id);
    }

    @Override
    public List<Merchantaddress> selectMerchantaddressByParentId(Long parentId)
    {
        return merchantaddressDao.selectMerchantaddressByParentId(parentId);
    }

    @Override
    public int selectCountDeviceinfoByMerchantaddressId(Long id)
    {
        return merchantaddressDao.selectCountDeviceinfoByMerchantaddressId(id);
    }

}
