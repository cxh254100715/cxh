package com.supervisions.modules.ten.service.impl;

import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.ten.dao.ITenPictureDao;
import com.supervisions.modules.ten.mapper.TenPicture;
import com.supervisions.modules.ten.service.ITenPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TenPicture 业务层处理
 */
@Service("tenPictureService")
@CacheConfig(cacheNames="tenPicture")
public class TenPictureServiceImpl implements ITenPictureService
{
    @Autowired
    private ITenPictureDao tenPictureDao;

    @Override
    public List<TenPicture> selectPictureList(TenPicture picture)
    {
        return tenPictureDao.selectPictureList(picture);
    }

    @Override
    public TenPicture selectTenPictureById(Long id)
    {
        return tenPictureDao.selectTenPictureById(id);
    }

    @Override
    @CachePut(key = "'pictureList:list'")
    public List<Map<String, Object>> savePicture(TenPicture picture)
    {
        int count = 0;
        Long id = picture.getId();
        if (StringUtils.isNotNull(id))
        {
            picture.setUpdateUser(ShiroUtils.getLoginName());
            picture.setUpdateTime(new Date());
            if(StringUtils.isNull(picture.getOrderNo())){
                picture.setOrderNo(0);
            }
            count = tenPictureDao.updatePicture(picture);
        }
        else
        {
            picture.setCreateUser(ShiroUtils.getLoginName());
            picture.setCreateTime(new Date());
            if(StringUtils.isNull(picture.getOrderNo())){
                picture.setOrderNo(0);
            }
            count = tenPictureDao.insertPicture(picture);
        }
        if(count>0){
            return tenPictureDao.selectTenPictureByType(0);
        }
        return null;
    }

    @Override
    //@CacheEvict(key = "'pictureList:list'")
    @CachePut(key = "'pictureList:list'")
    public List<Map<String, Object>> deleteTenPictureById(Long id)
    {
        int count = tenPictureDao.deleteTenPictureById(id);
        if(count>0){
            return tenPictureDao.selectTenPictureByType(0);
        }
        return null;
    }
}
