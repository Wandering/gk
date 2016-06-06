package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.IUniversityInfoDao;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
import cn.thinkjoy.gk.service.IReportResultService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 院校清单 --位次规则
 * Created by douzy on 16/3/14.
 */
@Service
public class UniversityInfoServiceImpl extends BaseUniversityInfoServiceImpl implements IUniversityInfoService  {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityInfoServiceImpl.class);
    @Resource
    IReportResultService iReportResultService;

    @Override
    public Integer selectPlanEnrolling(Map map) {
        return iUniversityInfoDao.selectPlanEnrolling(map);
    }

    /**
     * 版本控制
     * @param
     * @param
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoViewByVersion(UniversityInfoParmasView universityInfoParmasView) {
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();

        String tbName = ReportUtil.getTableName(universityInfoParmasView.getProvince(), universityInfoParmasView.getCategorie(),
                universityInfoParmasView.getBatch(), (universityInfoParmasView.getPrecedence() > 0 ? true : false));

        Map map = new HashMap<>();
        map.put("tableName", tbName);
        LOGGER.info("tableName:" + tbName);
        map.put("province", universityInfoParmasView.getProvince());//key
        map.put("majorType", universityInfoParmasView.getCategorie());

        switch (universityInfoParmasView.getVersion()) {
            case 1:               //线差及位次法
                if (universityInfoParmasView.getPrecedence() > 0) {
                    Integer result = iReportResultService.getPrecedence(tbName, universityInfoParmasView.getPrecedence());
                    map.put("precedence", result);

                } else {
                    LOGGER.info("==线差计算 Start==");
                    //根据分数及控制线 计算线差
                    Integer lineDiff = super.getLineDiff(universityInfoParmasView.getBatch(), universityInfoParmasView.getScore(),
                            universityInfoParmasView.getCategorie(), universityInfoParmasView.getProvince());
                    LOGGER.info("线差为:" + lineDiff);
                    LOGGER.info("==线差计算 End==");
                    map.put("scoreDiff", lineDiff);
                }
                universityInfoViews = super.selectUniversityInfo(map);
                break;
            case 2:    //排名法
                if (universityInfoParmasView.getPrecedence() > 0) {
                    Integer result = iReportResultService.getPrecedence(tbName, universityInfoParmasView.getPrecedence());
                    map.put("precedenceParmas", result);  // 计算线差
                }
                map.put("precedence", universityInfoParmasView.getPrecedence()); //user precedence
                map.put("first", universityInfoParmasView.getFirst());
                map.put("batch", universityInfoParmasView.getBatch());

                //判定算法走向
                boolean isScore = super.isScoreSupplementary(universityInfoParmasView);

                if (isScore) // true 走分数补充发    false 走位次法
                    universityInfoViews = super.selectUniversityInfoByScore(map);
                else
                    universityInfoViews = super.selectUniversityInfoByRanking(map);
                break;
        }
        return universityInfoViews;
    }

    @Override
    public List<Map<String, Object>> getUniversityEnrollingConditions(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityEnrollingConditions(map);
    }

    @Override
    public List<Map<String, Object>> getMajorEnrollingConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorEnrollingConditions(map);
    }

    @Override
    public List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorPlanConditions(map);
    }

    @Override
    public List<Map<String, Object>> getUniversityMajors(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityMajors(map);
    }

    @Override
    public List<Map<String, Object>> getUniversityEnrollingInfo(Map<String, String> map) {
        return iUniversityInfoDao.getUniversityEnrollingInfo(map);
    }

    @Override
    public List getUniversityMajorListByUniversityId(Map<String, Object> condition,int offset,int rows,String orderBy,String sortBy,Map<String, Object> selectorpage) {
        return iUniversityInfoDao.getUniversityMajorListByUniversityId(condition,offset,rows,orderBy,sortBy,selectorpage);
    }


}
