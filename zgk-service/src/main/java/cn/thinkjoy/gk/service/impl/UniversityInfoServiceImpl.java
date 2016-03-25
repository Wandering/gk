package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.entity.UniversityInfoView;
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
     * @param version
     * @param map
     * @return
     */
    @Override
    public List<UniversityInfoView> selectUniversityInfoViewByVersion(Integer version,Integer score,Integer categorie,String province,Integer batch,Integer precedence,Integer first) {
        List<UniversityInfoView> universityInfoViews = new ArrayList<>();

        String tbName = ReportUtil.getTableName(province, categorie, batch, (precedence > 0 ? true : false));

        Map map = new HashMap<>();
        map.put("tableName", tbName);
        LOGGER.info("tableName:" + tbName);
        map.put("province", province);//key
        map.put("majorType", categorie);

        switch (version) {
            case 1:               //线差及位次法
                if (precedence > 0) {
                    Integer result = iReportResultService.getPrecedence(tbName, precedence);
                    map.put("precedence", result);

                } else {
                    LOGGER.info("==线差计算 Start==");
                    //根据分数及控制线 计算线差
                    Integer lineDiff = super.getLineDiff(batch, score, categorie, province);
                    LOGGER.info("线差为:" + lineDiff);
                    LOGGER.info("==线差计算 End==");
                    map.put("scoreDiff", lineDiff);
                }
                universityInfoViews =  super.selectUniversityInfo(map);
                break;
            case 2:    //排名法
                map.put("precedence", precedence);
                map.put("first", first);
                universityInfoViews =  super.selectUniversityInfoByRanking(map);
                break;
        }
        return universityInfoViews;
    }

}
