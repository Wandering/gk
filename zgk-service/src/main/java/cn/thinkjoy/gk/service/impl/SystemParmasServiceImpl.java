package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportEnum;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.ISystemParmasDao;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.service.ISystemParmasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
@Service
public class SystemParmasServiceImpl implements ISystemParmasService {
    private static final Logger LOGGER= LoggerFactory.getLogger(SystemParmasServiceImpl.class);

    @Resource
    ISystemParmasDao iSystemParmasDao;



    @Override
    public SystemParmas selectModel(Map map) {
        return iSystemParmasDao.selectModel(map);
    }
    @Override
    public List<BatchView> selectSystemParmas( Integer cate ,Integer score,String provinceCode) {
        SystemParmas systemParmas=selectSystemParmas(cate,provinceCode);
        List<BatchView> batchViews=getBatchs(cate,systemParmas.getConfigValue(),score,provinceCode);
        return batchViews;
    }

    @Override
    public SystemParmas getThresoldModel(String proCode,String keyEnum) {
        Map parmasMap = new HashMap();
        parmasMap.put("configKey", proCode.toUpperCase()+ ReportUtil.ROLE_KEY_SPLIT_SYMBOL + keyEnum);
        parmasMap.put("provinceCode",proCode);
        SystemParmas systemParmas = selectModel(parmasMap);

        return systemParmas;
    }

    @Override
    public ArrayList<Integer> roleSplit(String configValue) {
        String[] roleArr = configValue.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
        ArrayList<Integer> roleValue = new ArrayList<>();
        for (String str : roleArr) {
            roleValue.add(Integer.valueOf(str));
        }
        return roleValue;
    }

    @Override
    public  SystemParmas getRoleByKey(String configKey) {
        Map map = new HashMap();
        map.put("configKey", configKey);
        return selectModel(map);
    }
    /**
     * 获取省份下文科参数配置
     * @param provinceCode
     * @return
     */
    private SystemParmas selectSystemParmas(int cate,String provinceCode) {
        String batchKey = getBatchKey(cate, provinceCode);
        Map parmasMap = new HashMap();
        parmasMap.put("configKey", batchKey);
        parmasMap.put("provinceCode",provinceCode);
        SystemParmas systemParmas = selectModel(parmasMap);
        return systemParmas;
    }

    /**
     * 根据分数 得到用户属于哪些批次
     * @return
     */
    private List<BatchView> getBatchs(Integer c,String batchStr ,Integer score,String provinceCode) {
        String[] batchArr = batchStr.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
        List<BatchView> batchViews = new ArrayList<>();
        for (int i = 0; i < batchArr.length; i++) {
            String batchLine = batchArr[i];
            if (score >= Integer.valueOf(batchLine)) {
                int batch = (i + 1);
                BatchView batchView = new BatchView();
                batchView.setBatch(batch);

                int ct = c == ReportEnum.categories.LI.getValue() ? ReportEnum.categories.WEN.getValue() : ReportEnum.categories.LI.getValue();
                SystemParmas systemParmasL = selectSystemParmas(ct, provinceCode);//理

                Integer batch1 = Integer.valueOf(batchLine);
                Integer batch2 = getBatchNumberLine(batch, systemParmasL.getConfigValue());

                if (c == ReportEnum.categories.LI.getValue()) {
                    batchView.setWenLine(batch2);
                    batchView.setLiLine(batch1);
                }
                if (c == ReportEnum.categories.WEN.getValue()) {
                    batchView.setWenLine(batch1);
                    batchView.setLiLine(batch2);
                }
                batchViews.add(batchView);
            }
        }
        return batchViews;
    }

    @Override
    public Integer getControleLine(Integer batch,int cate,String provinceCode) {
        SystemParmas systemParmas = selectSystemParmas(cate, provinceCode);
        return systemParmas != null ? getBatchNumberLine(batch, systemParmas.getConfigValue()) : 0;
    }

    /**
     * 获取某批次下控制线
     * @return
     */
    private Integer getBatchNumberLine(Integer batch,String configValue) {
        String[] lines = configValue.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
        return Integer.valueOf(lines[(batch - 1)]);
    }
    /**
     * 获取批次控制线 key
     * @return
     */
    @Override
    public String getBatchKey(Integer batch,String provinceCode) {
        return provinceCode.toUpperCase() + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + batch +ReportUtil.ROLE_KEY_SPLIT_SYMBOL+ ReportUtil.BATCHLINEKEY;
    }

    /**
     * 获取录取率规则
     * @param proCode
     * @return
     */
    @Override
    public   ArrayList<Integer> getEnrollRate(String proCode) {
        LOGGER.info("========录取率规则 start=======");
        SystemParmas systemParmas = getRoleByKey(proCode.toUpperCase() + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + ReportUtil.THRESHOLD_ENROLL_KEY);
        LOGGER.info("省份:" + proCode);
        LOGGER.info("规则串:" + systemParmas.getConfigValue());
        ArrayList<Integer> enrollRateArr = roleSplit(systemParmas.getConfigValue());
        LOGGER.info("========录取率规则 End=======");
        return enrollRateArr;
    }

    /**
     * 获取利用率规则
     * @param proCode
     * @return
     */
    @Override
    public   ArrayList<Integer> getUsedRate(String proCode) {
        LOGGER.info("========利用率规则 start=======");
        SystemParmas systemParmas = getRoleByKey(proCode.toUpperCase() + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + ReportUtil.THRESHOLD_USED_KEY);
        LOGGER.info("省份:" + proCode);
        LOGGER.info("规则串:" + systemParmas.getConfigValue());
        ArrayList<Integer> enrollRateArr = roleSplit(systemParmas.getConfigValue());
        LOGGER.info("========利用率规则 End=======");
        return enrollRateArr;
    }

    /**
     * 获取当前位次符合的排名规则区间下标
     * @return
     */
    @Override
    public Integer getRankingRangeIndex(String proCode,Integer precedence) {
        LOGGER.info("========获取排名规则区间下标 start=======");
        LOGGER.info("输入位次:" + precedence);
        SystemParmas systemParmas = getThresoldModel(proCode, ReportUtil.VOLUNTEER_BATCH_PRECEDENCE_KEY);
        if (systemParmas == null)
            return -1;
        LOGGER.info("组装排名VALUE:" + systemParmas.getConfigValue());
        Integer index = ReportUtil.getRankingRuleIndex(systemParmas.getConfigValue(), precedence);
        LOGGER.info("拆分取得下标:" + index);
        LOGGER.info("========获取排名规则区间下标 end=======");
        return index;
    }
}
