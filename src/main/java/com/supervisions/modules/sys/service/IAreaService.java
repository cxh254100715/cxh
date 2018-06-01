package com.supervisions.modules.sys.service;

import com.supervisions.modules.sys.mapper.Area;

import java.util.List;
import java.util.Map;

/**
 * area 业务层
 */
public interface IAreaService
{

    /**
     * 根据条件查询分页数据
     * @param area
     * @return
     */
    public List<Area> selectAreaList(Area area);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Area selectAreaById(Long id);

    /**
     * 检查名称是否唯一
     * @param area
     * @return
     */
    public String checkNameUnique(Area area);

    /**
     * 保存对象
     * @param area
     * @return
     */
    public int saveArea(Area area);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteAreaById(Long id);

    public List<Area> selectAreaByParentId(Long parentId);

    public Map<String,String> selectPCAById(Long id);
}
