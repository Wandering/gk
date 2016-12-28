package cn.thinkjoy.gk.dao.selcourse;

import org.apache.ibatis.annotations.Param;

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
    List queryPage(@Param("conditions") Map<String,Object> conditions,@Param("offset") int offset, @Param("rows") int rows,
                   @Param("orderBy") String orderBy,@Param("sortBy") String sortBy, @Param("selector") Map<String,Object> selector);


    /**
     * 分页统计方法
     * @param conditions
     * @return
     */
    int count(Map<String,Object> conditions);


    /**
     * 查询年份
     * @return
     */
    List queryYear();


    /**
     * 查询专业类别
     * @return
     */
    List queryMajorType();


    /**
     * 查询批次名称
     * @return
     */
    List queryBatchName();

    /**
     * 根据id查简介
     * @param id
     * @return
     */
    String getUniversityIntroById(Object id);
}
