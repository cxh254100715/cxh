package com.supervisions.modules.sys.dao;

import com.supervisions.modules.sys.mapper.Paramtype;

import java.util.List;

/**
 * paramtype 数据层
 * @author cxh
 */
public interface IParamtypeDao
{
    /**
     * 根据条件查询分页数据
     * @param paramtype
     * @return
     */
    public List<Paramtype> selectParamtypeList(Paramtype paramtype);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    public Paramtype selectParamtypeById(Long id);

    /**
     * 根据type查询对象
     * @param type
     * @return
     */
    public List<Paramtype> selectParamtypeByType(Integer type);

    /**
     * 检查名称是否唯一
     * @param name
     * @return
     */
    public Paramtype checkNameUnique(String name);

    /**
     * 新增对象
     * @param paramtype
     * @return
     */
    public int insertParamtype(Paramtype paramtype);

    /**
     * 更新对象
     * @param paramtype
     * @return
     */
    public int updateParamtype(Paramtype paramtype);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteParamtypeById(Long id);

    public int selectCountParamtypeByParentId(Long parentId);
}
