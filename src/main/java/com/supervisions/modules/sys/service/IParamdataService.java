package com.supervisions.modules.sys.service;

import com.supervisions.modules.sys.mapper.Paramdata;

import java.util.List;

/**
 * paramdata 业务层
 */
public interface IParamdataService
{

    /**
     * 根据条件查询分页数据
     * @param paramdata
     * @return
     */
    public List<Paramdata> selectParamdataList(Paramdata paramdata);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Paramdata selectParamdataById(Long id);

    /**
     * 根据paramId查询对象
     * @param paramId
     * @return
     */
    public List<Paramdata> selectParamdataByParamId(Long paramId);

    /**
     * 检查名称是否唯一
     * @param paramdata
     * @return
     */
    public String checkNameUnique(Paramdata paramdata);

    /**
     * 保存对象
     * @param paramdata
     * @return
     */
    public int saveParamdata(Paramdata paramdata);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteParamdataById(Long id);

    public int selectCountDeviceByParamdataId(Long paramdataId);

}
