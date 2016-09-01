package cn.thinkjoy.gk.service;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.entity.CheckBatchMsg;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.entity.UniversityEnrollView;
import cn.thinkjoy.gk.entity.UniversityInfoEnrolling;
import cn.thinkjoy.gk.pojo.ReportForecastView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import org.apache.commons.collections.map.HashedMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/1.
 */
public interface IScoreAlgorithmService {


    /**
     * 江苏算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */

    public Object recommendSchoolJS(float totalScore, long areaId, int majorType,long userId);

    /**
     * 浙江算法
     * @param totalScore
     * @param areaId
     * @return
     */
    public Object recommendSchoolZJ(float totalScore, long areaId,long userId);
    /**
     * 全国版算法
     * @param totalScore
     * @param areaId
     * @return
     */
    Object recommendSchool(float totalScore,long areaId,int majorType,long userId);

}
