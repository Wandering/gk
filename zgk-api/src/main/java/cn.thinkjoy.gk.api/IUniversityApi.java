package cn.thinkjoy.gk.api;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/11/1.
 *
 * 学校api
 */
public interface IUniversityApi {

    /**
     * 通过年份和地区获取批次
     * @param map
     * @return
     */
    List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map);

    /**
     * 根据type获取字典表信息
     *
     * @param type
     * @return
     */
    List getDataDictList(String type);


    List<String> getEnrollingYearsByProvinceId(long provinceId);

    /**
     * 专业招生信息查询条件
     * @param map
     * @return
     */
    List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map);

}
