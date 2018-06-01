package com.supervisions.modules.sys.service;

import com.supervisions.modules.sys.mapper.Paramtype;

import java.util.List;

/**
 * paramtype 业务层
 */
public interface IParamtypeService
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
     * 根据类型查询
     * @param type
     * @return
     */
    public List<Paramtype> selectParamtypeByType(Integer type);

    /**
     * 检查名称是否唯一
     * @param paramtype
     * @return
     */
    public String checkNameUnique(Paramtype paramtype);

    /**
     * 保存对象
     * @param paramtype
     * @return
     */
    public int saveParamtype(Paramtype paramtype);

    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public int deleteParamtypeById(Long id);

    public int selectCountParamtypeByParentId(Long id);
}
