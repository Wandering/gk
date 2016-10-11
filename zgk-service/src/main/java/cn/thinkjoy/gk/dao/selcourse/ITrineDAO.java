package cn.thinkjoy.gk.dao.selcourse;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/10.
 */
public interface ITrineDAO {
    /**
     * 分页方法
     * @param conditions
     * @return
     */
    List queryPage(Map<String,Object> conditions);

    /**
     * 分页统计方法
     * @param conditions
     * @return
     */
    int count(Map<String,Object> conditions);


    List queryYear();


    List queryMajorType();
}
