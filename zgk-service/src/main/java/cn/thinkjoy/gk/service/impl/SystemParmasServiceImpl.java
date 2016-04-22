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
    public SystemParmas getThresoldModel(String proCode,String keyEnum,Integer majorType) {
        Map parmasMap = new HashMap();
        parmasMap.put("configKey", proCode.toUpperCase()+ ReportUtil.ROLE_KEY_SPLIT_SYMBOL + keyEnum);
        parmasMap.put("provinceCode",proCode);
        parmasMap.put("majorType",majorType);
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
    public  SystemParmas getRoleByKey(String proCode,String configKey,Integer majorType) {
        Map map = new HashMap();
        map.put("configKey", configKey);
        map.put("provinceCode",proCode);
        map.put("majorType",majorType);
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
        parmasMap.put("provinceCode", provinceCode);
        parmasMap.put("majorType", cate);
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

            String[] batchLineArr = batchLine.split("\\|");

            if (batchLineArr.length > 1) {  // 区分 A B类
                for (int x = 0; x < batchLineArr.length; x++) {
                    Integer line = Integer.valueOf(batchLineArr[x]);
                    if (score >= line && line > 0) {
                        String btc = (i + 1) + "-" + String.valueOf(x + 1);
                        batchViews.add(ss(c, btc,x, provinceCode, batchLine));
                    }
                }
            } else {
                Integer btLine = Integer.valueOf(batchLine);
                if (score >= btLine && btLine > 0) {
                    int batch = (i + 1);
                    batchViews.add(ss(c, String.valueOf(batch),0, provinceCode, batchLine));
                }
            }
        }
        return batchViews;
    }
    private BatchView ss(Integer c,String viewBatch,Integer tagIndex ,String provinceCode,String batchLine) {
        BatchView batchView = new BatchView();
        batchView.setBatch(viewBatch);

        int ct = c == ReportEnum.categories.LI.getValue() ? ReportEnum.categories.WEN.getValue() : ReportEnum.categories.LI.getValue();
        SystemParmas systemParmasL = selectSystemParmas(ct, provinceCode);//理

        String batch1 = batchLine;
        String batch2 = getBatchNumberLine(viewBatch, systemParmasL.getConfigValue());
        String[] bthArr1= ReportUtil.getBatchTagArr(batch1);
        String[] bthArr2= ReportUtil.getBatchTagArr(batch2);

        if (c == ReportEnum.categories.LI.getValue()) {

            batchView.setWenLine(bthArr2[tagIndex]);
            batchView.setLiLine(bthArr1[tagIndex]);

        }
        if (c == ReportEnum.categories.WEN.getValue()) {
            batchView.setWenLine(bthArr1[tagIndex]);
            batchView.setLiLine(bthArr2[tagIndex]);
        }
        //获取对应配置信息
        SystemParmas wenSystemParmas = getThresoldModel(provinceCode, ReportUtil.CON_LINE_PLUS_VALUE_KEY, 1);
        SystemParmas liSystemParmas = getThresoldModel(provinceCode, ReportUtil.CON_LINE_PLUS_VALUE_KEY, 2);
        batchView.setWenPlus(wenSystemParmas == null ? 0 : Integer.valueOf(wenSystemParmas.getConfigValue()));
        batchView.setLiPlus(liSystemParmas == null ? 0 : Integer.valueOf(liSystemParmas.getConfigValue()));
        return batchView;
    }

    @Override
    public String getControleLine(String batch,int cate,String provinceCode) {
        SystemParmas systemParmas = selectSystemParmas(cate, provinceCode);
        return systemParmas != null ? getBatchNumberLine(batch, systemParmas.getConfigValue()) : "0";
    }

    /**
     * 获取某批次下控制线
     * @return
     */
    private String getBatchNumberLine(String batch,String configValue) {
        String[] lines = configValue.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);
        String[] arr = ReportUtil.getBatchArr(batch);
        return lines[(Integer.valueOf(arr[0]) - 1)];
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
    public   ArrayList<Integer> getEnrollRate(String proCode,Integer majorType) {
        LOGGER.info("========录取率规则 start=======");
        SystemParmas systemParmas = getRoleByKey(proCode,proCode.toUpperCase() + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + ReportUtil.THRESHOLD_ENROLL_KEY,majorType);
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
    public   ArrayList<Integer> getUsedRate(String proCode,Integer majorType) {
        LOGGER.info("========利用率规则 start=======");
        SystemParmas systemParmas = getRoleByKey(proCode,proCode.toUpperCase() + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + ReportUtil.THRESHOLD_USED_KEY,majorType);
        LOGGER.info("省份:" + proCode);
        LOGGER.info("规则串:" + systemParmas.getConfigValue());
        ArrayList<Integer> enrollRateArr = roleSplit(systemParmas.getConfigValue());
        LOGGER.info("========利用率规则 End=======");
        return enrollRateArr;
    }

    /**
     * 获取当前位次符合的排名规则区间下标   By 位次
     * @return
     */
    @Override
    public Integer getRankingRangeIndex(String proCode,Integer precedence,Integer majorType) {
        LOGGER.info("========获取排名规则区间下标 start=======");
        LOGGER.info("输入位次:" + precedence);
        SystemParmas systemParmas = getThresoldModel(proCode, ReportUtil.VOLUNTEER_BATCH_PRECEDENCE_KEY,majorType);
        if (systemParmas == null)
            return -1;
        LOGGER.info("组装排名VALUE:" + systemParmas.getConfigValue());
        Integer index = ReportUtil.getRankingRuleIndex(systemParmas.getConfigValue(), precedence);
        LOGGER.info("拆分取得下标:" + index);
        LOGGER.info("========获取排名规则区间下标 end=======");
        return index;
    }

    /**
     * 获取当前位次符合的排名规则区间下标  By 批次
     * @param batch
     * @param proCode
     * @param majorType
     * @return
     */
    @Override
    public Integer getRankingRangeIndex(String batch,String proCode,Integer majorType) {
        LOGGER.info("========获取排名规则区间下标 start=======");
        SystemParmas systemParmas = getThresoldModel(proCode, ReportUtil.SCORE_BATCH_ROLE_KEY, majorType);
        if (systemParmas == null)
            return -1;
        LOGGER.info("组装排名VALUE:" + systemParmas.getConfigValue());
        Integer index = ReportUtil.getRankingRuleIndex(batch, systemParmas.getConfigValue());
        LOGGER.info("拆分取得下标:" + index);
        LOGGER.info("========获取排名规则区间下标 start=======");
        return index;
    }
}
