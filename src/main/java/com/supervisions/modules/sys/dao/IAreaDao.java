package com.supervisions.modules.sys.dao;

import com.supervisions.modules.sys.mapper.Area;

import java.util.List;
import java.util.Map;

/**
 * area 数据层
 * @author cxh
 */
public interface IAreaDao
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
     * @param name
     * @return
     */
    public Area checkNameUnique(String name);

    /**
     * 新增对象
     * @param area
     * @return
     */
    public int insertArea(Area area);

    /**
     * 更新对象
     * @param area
     * @return
     */
    public int updateArea(Area area);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteAreaById(Long id);

    public List<Area> selectAreaByParentId(Long parentId);

    public Map<String,String> selectPCAById(Long id);
}
