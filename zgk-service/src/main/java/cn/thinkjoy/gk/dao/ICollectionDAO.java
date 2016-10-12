package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.MajorPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/12.
 */
public interface ICollectionDAO {
    int save(Map<String,Object> map);

    List<MajorPojo> queryPage(Map<String,Object> map);

    int count(Map<String,Object> map);
}
