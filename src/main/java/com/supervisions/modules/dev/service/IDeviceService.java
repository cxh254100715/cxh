package com.supervisions.modules.dev.service;

import com.supervisions.modules.dev.mapper.Device;

import java.util.List;

/**
 * device 业务层
 */
public interface IDeviceService
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
     * 根据type查询对象
     * @param type
     * @return
     */
    public List<Device> selectDeviceByType(Integer type);

    /**
     * 检查名称是否唯一
     * @param device
     * @return
     */
    public String checkNameUnique(Device device);

    /**
     * 保存对象
     * @param device
     * @return
     */
    public int saveDevice(Device device);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteDeviceById(Long id);

    public int selectCountDeviceinfoByDeviceId(Long id);

}
