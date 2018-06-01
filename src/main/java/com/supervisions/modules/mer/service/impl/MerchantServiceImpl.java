package com.supervisions.modules.mer.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.mer.dao.IMerchantDao;
import com.supervisions.modules.mer.mapper.Merchant;
import com.supervisions.modules.mer.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * merchant 业务层处理
 */
@Service("merchantService")
public class MerchantServiceImpl implements IMerchantService
{

    @Autowired
    private IMerchantDao merchantDao;

    @Override
    public List<Merchant> selectMerchantList(Merchant merchant)
    {
        return merchantDao.selectMerchantList(merchant);
    }

    @Override
    public List<Merchant> selectAllMerchants()
    {
        return merchantDao.selectAllMerchants();
    }

    @Override
    public Merchant selectMerchantById(Long id)
    {
        return merchantDao.selectMerchantById(id);
    }

    @Override
    public String checkNameUnique(Merchant merchant)
    {
        Long id = merchant.getId();
        Merchant info = merchantDao.checkNameUnique(merchant.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveMerchant(Merchant merchant)
    {
        int count = 0;
        Long id = merchant.getId();
        if (StringUtils.isNotNull(id))
        {
            merchant.setUpdateUser(ShiroUtils.getLoginName());
            merchant.setUpdateTime(new Date());
            count = merchantDao.updateMerchant(merchant);
        }
        else
        {
            merchant.setCreateUser(ShiroUtils.getLoginName());
            merchant.setCreateTime(new Date());
            count = merchantDao.insertMerchant(merchant);
        }
        return count;
    }

    @Override
    public int deleteMerchantById(Long id)
    {
        return merchantDao.deleteMerchantById(id);
    }

    @Override
    public int selectCountMerchantaddressByParentId(Long parentId)
    {
        return merchantDao.selectCountMerchantaddressByParentId(parentId);
    }

}
