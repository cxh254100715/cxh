package com.supervisions.modules.ten.service;

import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.ten.mapper.TenPicture;

import java.util.List;
import java.util.Map;

public interface ITenPictureService
{

    /**
     * 列表
     * @param picture
     * @return
     */
    public List<TenPicture> selectPictureList(TenPicture picture);

    public TenPicture selectTenPictureById(Long id);

    public List<Map<String, Object>> savePicture(TenPicture picture);

    public List<Map<String, Object>> deleteTenPictureById(Long id);
}
