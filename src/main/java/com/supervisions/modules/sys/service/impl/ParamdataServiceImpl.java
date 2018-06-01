package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IParamdataDao;
import com.supervisions.modules.sys.mapper.Paramdata;
import com.supervisions.modules.sys.service.IParamdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * paramdata 业务层处理
 */
@Service("paramdataService")
public class ParamdataServiceImpl implements IParamdataService
{

    @Autowired
    private IParamdataDao paramdataDao;

    @Override
    public List<Paramdata> selectParamdataList(Paramdata paramdata)
    {
        return paramdataDao.selectParamdataList(paramdata);
    }

    @Override
    public Paramdata selectParamdataById(Long id)
    {
        return paramdataDao.selectParamdataById(id);
    }

    @Override
    public List<Paramdata> selectParamdataByParamId(Long paramId)
    {
        return paramdataDao.selectParamdataByParamId(paramId);
    }

    @Override
    public String checkNameUnique(Paramdata paramdata)
    {
        Long id = paramdata.getId();
        Paramdata info = paramdataDao.checkNameUnique(paramdata.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveParamdata(Paramdata paramdata)
    {
        int count = 0;
        Long id = paramdata.getId();
        if (StringUtils.isNotNull(id))
        {
            paramdata.setUpdateUser(ShiroUtils.getLoginName());
            paramdata.setUpdateTime(new Date());
            count = paramdataDao.updateParamdata(paramdata);
        }
        else
        {
            paramdata.setCreateUser(ShiroUtils.getLoginName());
            paramdata.setCreateTime(new Date());
            count = paramdataDao.insertParamdata(paramdata);
        }
        return count;
    }

    @Override
    public int deleteParamdataById(Long id)
    {
        return paramdataDao.deleteParamdataById(id);
    }

    @Override
    public int selectCountDeviceByParamdataId(Long paramdataId)
    {
        return paramdataDao.selectCountDeviceByParamdataId(paramdataId);
    }


}
