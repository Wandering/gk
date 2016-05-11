package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.entity.ProvinceInfo;

import java.util.Map;

/**
 * Created by douzy on 16/5/11.
 */
public interface IProvinceInfoDao  {
    ProvinceInfo getProvinceInfoByCode(Map map);
}
