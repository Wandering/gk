package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;

import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface IUniversityInfoService extends IBaseUniversityInfoService {

    /**
     * 获取招生计划数
     *
     * @param map
     * @return
     */
    public Integer selectPlanEnrolling(Map map);

    /**
     * 获取院校清单  根据接口版本号
     * @param universityInfoParmasView
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoViewByVersion(UniversityInfoParmasView universityInfoParmasView);

    /**
     *  获取院校清单  根据逻辑走向
     * @param universityInfoParmasView
     * @return
     */
    public List<UniversityInfoView> selectUniversityInfoViewByLogic(UniversityInfoParmasView universityInfoParmasView);

    /**
     * 分数转换位次
     * @param parmasView
     * @return
     */
    public Integer converPreByScore(ReportForecastView parmasView,String key );


    /**
     * 分数转换位次  ---录取难易预测
     * @param parmasView
     * @return
     */
    public Integer converPreByScoreV2(ReportForecastView parmasView,String key );


    /**
     * 分数转换线差
     * @param parmasView
     * @return
     */
    public Integer converScoreDiffByScore(ReportForecastView parmasView);
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

    /**
     * 专业招生信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getUniversityMajors(Map<String, String> map);

    /**
     * 特色专业查询
     * @param universityId
     * @return
     */
    List<Map<String, Object>> getUniversityspecialMajors(Object universityId);

    /**
     * 专业招生信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getUniversityEnrollingInfo(Map<String, String> map);

    List getUniversityMajorListByUniversityId(Map<String, Object> condition,int offset,int rows,String orderBy,String sortBy,Map<String, Object> selectorpage);

    /**
     * 通过年份和地区获取批次
     * @param map
     * @return
     */
    List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map);

    /**
     * 难易预测
     * @param reportForecastView
     * @return
     */
    String getEnrollingByForecast(ReportForecastView reportForecastView);


    /**
     * 难易预测位次
     * @param reportParm
     * @return
     */
    public List<UniversityInfoEnrolling> getUniversityEnrollingsByPrecedence(ReportForecastView reportParm);

    /**
     * 难易预测线差
     * @param reportParm
     * @return
     */
    public List<UniversityInfoEnrolling> getUniversityEnrollingsByScoreDiff(ReportForecastView reportParm);


    /**
     * 成绩分析 获取院校录取信息
     * @param condition
     * @param sortBy
     * @return
     */

    List<UniversityEnrollView> selectUnivEnrollInfo(List<Map<String, Object>> maps,boolean isJoin,
                                                    String sortBy);


    /**
     * 成绩分析 获取院校录取信息
     * @param condition
     * @return
     */

    List<Long> selectUnivInfoIdInBatch(Map<String, Object> condition);

}
