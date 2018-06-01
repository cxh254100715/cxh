package com.supervisions.modules.sys.service;

import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.mapper.Versions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app版本 业务层
 */
public interface IVersionsService {

    /**
     * 根据条件分页查询结果
     * @param versions
     * @return
     */
    public List<Versions> selectVersionsList(Versions versions);

    /**
     * 保存
     * @param versions
     * @return
     */
    public int saveVersions(Versions versions);

    /**
     * 根据id查询
     * @param versions
     * @return
     */
    public Versions selectVersionsById(Long versions);

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



}
