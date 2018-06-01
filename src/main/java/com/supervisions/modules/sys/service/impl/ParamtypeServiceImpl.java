package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IParamtypeDao;
import com.supervisions.modules.sys.mapper.Paramtype;
import com.supervisions.modules.sys.service.IParamtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * paramtype 业务层处理
 */
@Service("paramtypeService")
public class ParamtypeServiceImpl implements IParamtypeService
{

    @Autowired
    private IParamtypeDao paramtypeDao;

    @Override
    public List<Paramtype> selectParamtypeList(Paramtype paramtype)
    {
        return paramtypeDao.selectParamtypeList(paramtype);
    }

    @Override
    public Paramtype selectParamtypeById(Long id)
    {
        return paramtypeDao.selectParamtypeById(id);
    }

    @Override
    public List<Paramtype> selectParamtypeByType(Integer type)
    {
        return paramtypeDao.selectParamtypeByType(type);
    }

    @Override
    public String checkNameUnique(Paramtype paramtype)
    {
        Long id = paramtype.getId();
        Paramtype info = paramtypeDao.checkNameUnique(paramtype.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveParamtype(Paramtype paramtype)
    {
        int count = 0;
        Long id = paramtype.getId();
        if (StringUtils.isNotNull(id))
        {
            paramtype.setUpdateUser(ShiroUtils.getLoginName());
            paramtype.setUpdateTime(new Date());
            count = paramtypeDao.updateParamtype(paramtype);
        }
        else
        {
            paramtype.setCreateUser(ShiroUtils.getLoginName());
            paramtype.setCreateTime(new Date());
            count = paramtypeDao.insertParamtype(paramtype);
        }
        return count;
    }

    @Override
    public int deleteParamtypeById(Long id)
    {
        return paramtypeDao.deleteParamtypeById(id);
    }

    @Override
    public int selectCountParamtypeByParentId(Long parentId)
    {
        return paramtypeDao.selectCountParamtypeByParentId(parentId);
    }
    
}
