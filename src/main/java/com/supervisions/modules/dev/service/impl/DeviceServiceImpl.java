package com.supervisions.modules.dev.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.dev.dao.IDeviceDao;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.dev.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * device 业务层处理
 */
@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService
{

    @Autowired
    private IDeviceDao deviceDao;

    @Override
    public List<Device> selectDeviceList(Device device)
    {
        return deviceDao.selectDeviceList(device);
    }

    @Override
    public Device selectDeviceById(Long id)
    {
        return deviceDao.selectDeviceById(id);
    }

    @Override
    public List<Device> selectDeviceByType(Integer tpye)
    {
        return deviceDao.selectDeviceByType(tpye);
    }

    @Override
    public String checkNameUnique(Device device)
    {
        Long id = device.getId();
        Device info = deviceDao.checkNameUnique(device.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveDevice(Device device)
    {
        int count = 0;
        Long id = device.getId();
        if (StringUtils.isNotNull(id))
        {
            device.setUpdateUser(ShiroUtils.getLoginName());
            device.setUpdateTime(new Date());
            count = deviceDao.updateDevice(device);
        }
        else
        {
            device.setCreateUser(ShiroUtils.getLoginName());
            device.setCreateTime(new Date());
            count = deviceDao.insertDevice(device);
        }
        return count;
    }

    @Override
    public int deleteDeviceById(Long id)
    {
        return deviceDao.deleteDeviceById(id);
    }

    @Override
    public int selectCountDeviceinfoByDeviceId(Long id)
    {
        return deviceDao.selectCountDeviceinfoByDeviceId(id);
    }

}
