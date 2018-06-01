package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IAreaDao;
import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.sys.mapper.Menu;
import com.supervisions.modules.sys.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<Area> selectAreaList1(Long parentId)
    {
        return areaDao.selectAreaList1(parentId);
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

    @Override
    public List<Map<String, Object>> areaTreeData()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Area> areaList = areaDao.selectAreaTreeAll();
        trees = getTrees(areaList);
        return trees;
    }

    /**
     * 对象转菜单树
     */
    public List<Map<String, Object>> getTrees(List<Area> areaList)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Area area : areaList)
        {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", area.getId());
            deptMap.put("pId", area.getParentId());
            deptMap.put("name", area.getName());
            trees.add(deptMap);
        }
        return trees;
    }


}
