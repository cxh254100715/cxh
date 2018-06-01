package com.supervisions.modules.dev.dao;

import com.supervisions.modules.dev.mapper.Deviceinfo;

import java.util.List;

/**
 * deviceinfo 数据层
 * @author cxh
 */
public interface IDeviceinfoDao
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
     * @param name
     * @return
     */
    public Deviceinfo checkNameUnique(String name);

    /**
     * 新增对象
     * @param deviceinfo
     * @return
     */
    public int insertDeviceinfo(Deviceinfo deviceinfo);

    /**
     * 更新对象
     * @param deviceinfo
     * @return
     */
    public int updateDeviceinfo(Deviceinfo deviceinfo);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteDeviceinfoById(Long id);
    
}
