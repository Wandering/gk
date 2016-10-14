package cn.thinkjoy.gk.service.selcourse;

import cn.thinkjoy.gk.pojo.MajorPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/12.
 */
public interface ICollectionService {

    boolean save(Map<String,Object> map);

    boolean check(Map<String,Object> map);

    boolean delete(Map<String,Object> map);

    List<MajorPojo> queryPage(Map<String,Object> map);

    int count(Map<String,Object> map);

}
