package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.entity.UniversityInfoView;
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
     * 专业招生信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getUniversityEnrollingInfo(Map<String, String> map);

}
