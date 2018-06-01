package com.supervisions.modules.dev.service;

import com.supervisions.modules.dev.mapper.Deviceinfo;

import java.util.List;

/**
 * deviceinfo 业务层
 */
public interface IDeviceinfoService
{

    /**
     * 根据条件查询分页数据
     * @param deviceinfo
     * @return
     */
    public List<Deviceinfo> selectDeviceinfoList(Deviceinfo deviceinfo);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Deviceinfo selectDeviceinfoById(Long id);

    /**
     * 检查名称是否唯一
     * @param deviceinfo
     * @return
     */
    public String checkNameUnique(Deviceinfo deviceinfo);

    /**
     * 保存对象
     * @param deviceinfo
     * @return
     */
    public int saveDeviceinfo(Deviceinfo deviceinfo);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteDeviceinfoById(Long id);

}
