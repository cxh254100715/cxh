package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IAreaDao;
import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.sys.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * area 业务层处理
 */
@Service("areaService")
public class AreaServiceImpl implements IAreaService
{

    @Autowired
    private IAreaDao areaDao;

    @Override
    public List<Area> selectAreaList(Area area)
    {
        return areaDao.selectAreaList(area);
    }

    @Override
    public Area selectAreaById(Long id)
    {
        return areaDao.selectAreaById(id);
    }

    @Override
    public String checkNameUnique(Area area)
    {
        Long id = area.getId();
        Area info = areaDao.checkNameUnique(area.getName());
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getId()) && !info.getId().equals(id))
        {
            return UserConstants.NAME_NOT_UNIQUE;
        }
        return UserConstants.NAME_UNIQUE;
    }

    @Override
    public int saveArea(Area area)
    {
        int count = 0;
        Long id = area.getId();
        if (StringUtils.isNotNull(id))
        {
            area.setUpdateUser(ShiroUtils.getLoginName());
            area.setUpdateTime(new Date());
            count = areaDao.updateArea(area);
        }
        else
        {
            area.setCreateUser(ShiroUtils.getLoginName());
            area.setCreateTime(new Date());
            count = areaDao.insertArea(area);
        }
        return count;
    }

    @Override
    public int deleteAreaById(Long id)
    {
        return areaDao.deleteAreaById(id);
    }

    @Override
    public List<Area> selectAreaByParentId(Long parentId)
    {
        return areaDao.selectAreaByParentId(parentId);
    }

    @Override
    public Map<String, String> selectPCAById(Long id)
    {
        return areaDao.selectPCAById(id);
    }


}
