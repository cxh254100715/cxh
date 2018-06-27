package com.supervisions.modules.ten.dao;

import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.ten.mapper.TenPicture;

import java.util.List;
import java.util.Map;

/**
 * TenPicture 数据层
 * @author cxh
 */
public interface ITenPictureDao
{
    /**
     * 列表
     * @param picture
     * @return
     */
    public List<TenPicture> selectPictureList(TenPicture picture);

    public TenPicture selectTenPictureById(Long id);

    public int savePicture(TenPicture picture);

    public int updatePicture(TenPicture picture);

    public int insertPicture(TenPicture picture);

    public int deleteTenPictureById(Long id);
}
