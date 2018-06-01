package com.supervisions.modules.sys.service.impl;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.modules.sys.dao.IVersionsDao;
import com.supervisions.modules.sys.mapper.Versions;
import com.supervisions.modules.sys.service.IVersionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app版本 业务逻辑层
 */
@Service("versionsService")
public class VersionsServiceImpl implements IVersionsService
{

    @Autowired
    private IVersionsDao versionsDao;


    /**
     * 根据条件分页查询对象
     * @param versions
     * @return
     */
    @Override
    public List<Versions> selectVersionsList(Versions versions)
    {
        return versionsDao.selectVersionsList(versions);
    }

    /**
     * 保存
     * @param versions
     * @return
     */
    @Override
    public int saveVersions(Versions versions)
    {
        int count = 0;
        //versions.setName(versions.getVersionName()+versions.getVersionCode());
        versions.setCreateUser(ShiroUtils.getLoginName());
        count = versionsDao.insertVersions(versions);
        return count;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Versions selectVersionsById(Long id)
    {
        return versionsDao.selectVersionsById(id);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public int deleteVersionsById(Long id)
    {
        return versionsDao.deleteVersionsById(id);
    }

    /**
     * 检查版本号唯一性
     * @param map
     * @return
     */
    @Override
    public int checkCodeUnique(Map<String,Object> map)
    {
        return versionsDao.checkCodeUnique(map);
    }

}
