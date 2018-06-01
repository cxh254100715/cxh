package com.supervisions.framework.web.dao;

import com.supervisions.modules.mer.mapper.Merchantaddress;

import java.util.List;

public interface IBaseDao<T>
{

    public List<T> selectList(T obj);

    public T selectById(Long id);

    public T checkNameUnique(String name);

    public int insert(T obj);

    public int update(T obj);

    public int deleteById(Long id);



}
