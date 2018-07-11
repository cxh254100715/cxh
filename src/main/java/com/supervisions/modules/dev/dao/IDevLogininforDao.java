package com.supervisions.modules.dev.dao;

import org.apache.ibatis.annotations.Param;

public interface IDevLogininforDao
{

    public int deleteLogininforByDeviceIdAndType(@Param("deviceId") Long deviceId, @Param("type") Integer type);
}
