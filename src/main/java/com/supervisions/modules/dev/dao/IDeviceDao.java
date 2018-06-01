package com.supervisions.modules.dev.dao;

import com.supervisions.modules.dev.mapper.Device;

import java.util.List;

/**
 * device 数据层
 * @author cxh
 */
public interface IDeviceDao
{
    /**
     * 根据条件查询分页数据
     * @param device
     * @return
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Device selectDeviceById(Long id);

    /**
     * 根据类型查询对象
     * @param type
     * @return
     */
    public List<Device> selectDeviceByType(Integer type);

    /**
     * 检查名称是否唯一
     * @param name
     * @return
     */
    public Device checkNameUnique(String name);

    /**
     * 新增对象
     * @param device
     * @return
     */
    public int insertDevice(Device device);

    /**
     * 更新对象
     * @param device
     * @return
     */
    public int updateDevice(Device device);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteDeviceById(Long id);

    public int selectCountDeviceinfoByDeviceId(Long id);
}
