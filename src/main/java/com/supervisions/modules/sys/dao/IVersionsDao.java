package com.supervisions.modules.sys.dao;

import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.mapper.Versions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app版本 数据层
 */
public interface IVersionsDao
{

    /**
     * 根据条件分页查询对象
     * @param versions
     * @return
     */
    public List<Versions> selectVersionsList(Versions versions);

    /**
     * 新增
     * @param versions
     * @return
     */
    public int insertVersions(Versions versions);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Versions selectVersionsById(Long id);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public int deleteVersionsById(Long id);

    /**
     * 检查版本唯一性
     * @return
     */
    public int checkCodeUnique(Map<String,Object> map);

    /**
     * 获取最大版本号
     * @return
     */
    public Versions getMaxCode(Integer type);

}
