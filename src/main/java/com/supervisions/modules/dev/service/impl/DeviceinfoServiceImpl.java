package com.supervisions.modules.dev.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.dev.dao.IDeviceinfoDao;
import com.supervisions.modules.dev.dao.IDevLogininforDao;
import com.supervisions.modules.dev.mapper.Deviceinfo;
import com.supervisions.modules.dev.service.IDeviceinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * deviceinfo 业务层处理
 */
@Service("deviceinfoService")
public class DeviceinfoServiceImpl implements IDeviceinfoService
{

    @Autowired
    private IDeviceinfoDao deviceinfoDao;
    @Autowired
    private IDevLogininforDao logininforDao;

    @Override
    public List<Deviceinfo> selectDeviceinfoList(Deviceinfo deviceinfo)
    {
        return deviceinfoDao.selectDeviceinfoList(deviceinfo);
    }

    @Override
    public Deviceinfo selectDeviceinfoById(Long id)
    {
        return deviceinfoDao.selectDeviceinfoById(id);
    }

    @Override
    public String checkNameUnique(Deviceinfo deviceinfo)
    {
        Long id = deviceinfo.getId();
        Deviceinfo info = deviceinfoDao.checkNameUnique(deviceinfo.getDeviceSn());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveDeviceinfo(Deviceinfo deviceinfo)
    {
        int count = 0;
        Long id = deviceinfo.getId();
        if (StringUtils.isNotNull(id))
        {
            deviceinfo.setUpdateUser(ShiroUtils.getLoginName());
            deviceinfo.setUpdateTime(new Date());
            count = deviceinfoDao.updateDeviceinfo(deviceinfo);
        }
        else
        {
            deviceinfo.setStatus("1");
            deviceinfo.setIsActivated(0);
            deviceinfo.setCreateUser(ShiroUtils.getLoginName());
            deviceinfo.setCreateTime(new Date());
            count = deviceinfoDao.insertDeviceinfo(deviceinfo);
        }
        return count;
    }

    @Override
    public int deleteDeviceinfoById(Long id,Integer type)
    {

        logininforDao.deleteLogininforByDeviceIdAndType(id,type);
        return deviceinfoDao.deleteDeviceinfoById(id);
    }
    
}
