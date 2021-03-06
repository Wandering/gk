package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface IUniversityInfoDao extends IBaseDAO<UniversityInfoView> {
    /**
     * 院校清单List    --- 有位次 按位次   没有位次  按分数
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfo(Map map);
    /**
     * 院校清单List    --- 按院校排名   规则见DB zgk_system_parmas
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByRanking(Map map);

    /**
     * 院校清单List    --- 按线差   规则见DB zgk_system_parmas
     * @param map
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoByLineDiff(Map map);
    /**
     * 难易预测&成绩分析
     * @param map
     * @return
     */
    List<UniversityInfoEnrolling> selectUniversityEnrolling(Map map);

    /**
     * 获取计划招生数
     * @param map
     * @return
     */
    Integer selectPlanEnrolling(Map map);

    /**
     * 院校录取信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getUniversityEnrollingConditions(Map<String, String> map);

    /**
     * 专业录取信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getMajorEnrollingConditions(Map<String, String> map);

    /**
     * 专业招生信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map);

    List<Map<String, Object>> getUniversityMajors(Map<String, String> map);

    List<Map<String, Object>> getUniversityspecialMajors(Object universityId);

    List<Map<String, Object>> getUniversityEnrollingInfo(Map<String, String> map);


    /**
     * 通过年份和地区获取批次
     * @param map
     * @return
     */
    List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map);

    List getUniversityMajorListByUniversityId(@Param("condition") Map<String, Object> condition, @Param("offset") int offset, @Param("rows") int rows,
                             @Param("orderBy") String orderBy, @Param("sortBy") String sortBy,@Param("selector")Map<String, Object> selectorpage);

    /**
     * 查询学校录取信息
     * @param condition
     * @param sortBy
     * @return
     */
    List<UniversityEnrollView> selectUnivEnrollInfo(@Param("maps") List<Map<String, Object>> condition,@Param("isJoin") boolean isJoin,
                                                    @Param("sortBy") String sortBy);

    /**
     * 查询学校是否在该批次录取
     * @param condition
     * @return
     */
    List<Long> selectUnivInfoIdInBatch(@Param("condition") Map<String, Object> condition);

    /**
     * 根据省份ID和学校所在省份ID查询录取年份集合
     *
     * @param currentProId
     * @param schoolProId
     * @return
     */
    List<String> getEnrollingYearsByProvinceId(
            @Param("currentProId") long currentProId,
            @Param("schoolProId") long schoolProId
    );

}
