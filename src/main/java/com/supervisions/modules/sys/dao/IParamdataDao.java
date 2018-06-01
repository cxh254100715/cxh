package com.supervisions.modules.sys.dao;

import com.supervisions.modules.sys.mapper.Paramdata;

import java.util.List;

/**
 * paramdata 数据层
 * @author cxh
 */
public interface IParamdataDao
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
     * @param name
     * @return
     */
    public Paramdata checkNameUnique(String name);

    /**
     * 新增对象
     * @param paramdata
     * @return
     */
    public int insertParamdata(Paramdata paramdata);

    /**
     * 更新对象
     * @param paramdata
     * @return
     */
    public int updateParamdata(Paramdata paramdata);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteParamdataById(Long id);

    public int selectCountDeviceByParamdataId(Long paramdataId);
}
