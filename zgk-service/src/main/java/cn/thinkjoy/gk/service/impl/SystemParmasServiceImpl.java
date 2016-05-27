package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ReportEnum;
import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.dao.ISystemParmasDao;
import cn.thinkjoy.gk.entity.CheckBatchMsg;
import cn.thinkjoy.gk.entity.SystemParmas;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.pojo.UniversityInfoParmasView;
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
    public List<BatchView> selectSystemParmas( Integer cate ,Integer score, String provinceCode) {
        SystemParmas systemParmas=selectSystemParmas(cate,provinceCode);
        List<BatchView> batchViews=getBatchs(cate,systemParmas.getConfigValue(),score,provinceCode);
        return batchViews;
    }

    /**
     * 获取是否优先
     * @param num   --如果是线差  传入 分数    是位次  传入位次
     * @param provinceCode
     * @param batch
     * @param cate
     * @return
     */
    private boolean getFirst(Integer num,String provinceCode,String batch,Integer cate,Integer logic) {


        ReportEnum.LogicTrend logicTrend = ReportEnum.LogicTrend.getLogic(logic);
        String firstKey = ReportUtil.PRECEDENCE_SP_FIRST_OR_UN_FIRST_KEY;
        Integer exValue = num;
        Integer rangeIndex = -1;
        if (logicTrend.equals(ReportEnum.LogicTrend.LINEDIFF)) {
            firstKey = ReportUtil.SP_FIRST_OR_UN_FIRST_KEY + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + batch;
            exValue = getLineDiff(batch, num, cate, provinceCode);
            rangeIndex = getLineDiffRangeIndex(exValue, provinceCode, cate, batch);
        }

        SystemParmas systemParmas = getThresoldModel(provinceCode, firstKey, cate);
        if (systemParmas == null)
            return false;

        rangeIndex = getRankingRangeIndex(provinceCode, exValue, cate,batch);

        String[] firstArr = systemParmas.getConfigValue().split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);

        String firstRange = firstArr[rangeIndex];

        return Integer.valueOf(firstRange) == 1 ? true : false;
    }

    /**
     * 获取批次状态
     * @param cate 科类
     * @param score 分数
     * @param provinceCode 省份
     * @param logicTrend 逻辑
     * @return
     */
    @Override
    public List<BatchView> selectSystemParmas( Integer cate ,Integer score, Integer sap,String provinceCode, Integer logicTrend) {
        SystemParmas systemParmas = selectSystemParmas(cate, provinceCode);
        List<BatchView> batchViews = new ArrayList<>();
        //拆分批次
        String[] batchArr = systemParmas.getConfigValue().split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);

        Integer flag=-1;

        boolean isRecom=true
                ,first=false;
        for (int i = 0; i < batchArr.length; i++) {
            String batchLine = batchArr[i];
            //拆分 A类 B类 批次
            String[] batchLineArr = batchLine.split("\\|");
            if (batchLineArr.length > 1) {  // >1 A B类 <=1 正常
                for (int x = 0; x < batchLineArr.length; x++) {
                    /***************************AB类后续处理**************************/
                    Integer line = Integer.valueOf(batchLineArr[x]);

                    String btc = (i + 1) + "-" + String.valueOf(x + 1);
                    BatchView batchView = batchConfig(cate, btc, x, provinceCode, batchLine);

                    boolean isFirst = !first ? getFirst(sap, provinceCode, btc, cate,logicTrend) : first;

                    batchView.setFirst(first ? false : isFirst);

                    first = isFirst;

                    if (score >= line && line > 0) {
                        batchView.setConform(true);
                        if (i == flag) {
                            batchView.setRecommend(true);
                            isRecom = false;
                        }
                        //是否是压线生
                        if (isLine(logicTrend, btc, cate, provinceCode, score)) {
                            flag = (i + 1) == 2 ? 3 : (i + 1);  //三批特殊处理  0：一批 1：二批 3：高职高专 4：三批
                            batchView.setIsLine(true);
                            isRecom = false;
                        } else if (isRecom) {
                            batchView.setIsLine(false);
                            batchView.setRecommend(true);
                            isRecom = false;
                        }
                    }else
                        batchView.setConform(false);

                    batchViews.add(batchView);
                }
            } else {
                Integer btLine = Integer.valueOf(batchLine);
                String batch = String.valueOf((i + 1));
                BatchView batchView = batchConfig(cate, batch, 0, provinceCode, batchLine);

                boolean isFirst = !first ? getFirst(sap, provinceCode, batch, cate,logicTrend) : first;
                batchView.setFirst(first ? false : isFirst);
                first = isFirst;
                //批次线大于0 且分数大于等于批次线 表示用户分数已达标
                if (btLine > 0 && score >= btLine) {
                    batchView.setConform(true);
                    if (i == flag) {
                        batchView.setRecommend(true);
                        isRecom = false;
                    }
                    //是否是压线生
                    if (isLine(logicTrend, batch, cate, provinceCode, score)) {
                        flag = (i + 1) == 2 ? 3 : (i + 1);  //三批特殊处理  0：一批 1：二批 3：高职高专 4：三批
                        batchView.setIsLine(true);
                        isRecom = false;
                    } else if (isRecom) {
                        batchView.setIsLine(false);
                        batchView.setRecommend(true);
                        isRecom = false;
                    }
                } else
                    batchView.setConform(false);

                batchViews.add(batchView);
            }
        }

        return batchViews;
    }
    @Override
    public Integer getLineDiff(String batch, Integer score, Integer cate, String provinceCode) {
        String[] bch = batch.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
        String controleLine = getControleLine(bch[0], cate, provinceCode);
        String[] conLineArr = controleLine.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
        Integer line = 0;
        if (conLineArr.length > 1) {
            line = (score - Integer.valueOf(conLineArr[Integer.valueOf(bch[1])]));
        } else
            line = (score - Integer.valueOf(conLineArr[0]));

        return line;
    }
    @Override
    public boolean isScoreSupplementaryLindDiff(UniversityInfoParmasView parmasView) {
        boolean result = false;
        if (isOpenScore(parmasView)) {
            //获取线差值
            Integer line = getLineDiff(parmasView.getBatch(), parmasView.getScore(), parmasView.getScore(), parmasView.getProvince());
            SystemParmas systemParmas = getThresoldModel(parmasView.getProvince(), ReportUtil.LINE_DIFF_CON_LINE_PLUS_VALUE, parmasView.getCategorie());
            if (systemParmas == null)
                return false;
            //压线生
            if (line >= 0 && line <= Integer.valueOf(systemParmas.getConfigValue()))
                result = true;
        }
        return result;
    }
    /**
     * 是否开启线差法
     * @param parmasView
     * @return
     */
    private boolean isOpenScore(UniversityInfoParmasView parmasView) {
        //获取对应配置信息
        SystemParmas systemParmas = getThresoldModel(parmasView.getProvince(), ReportUtil.SCORE_ROLE_KEY, parmasView.getCategorie());
        if (systemParmas == null)
            return false;
        Integer isScore = Integer.valueOf(systemParmas.getConfigValue());
        //1:为开启分数补充法
        return isScore == 1;
    }
    @Override
    public boolean isScoreSupplementary(UniversityInfoParmasView parmasView) {
        boolean result = false;
        //是否开启
        if (isOpenScore(parmasView)) {

            String conLineScore = getControleLine(parmasView.getBatch(), parmasView.getCategorie(), parmasView.getProvince());

            String[] conLineArr=conLineScore.split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);
            String[] batArr = ReportUtil.getBatchArr(parmasView.getBatch());

             String conlineKey=ReportUtil.getExecKey(parmasView.getProvince(),ReportUtil.CON_LINE_PLUS_VALUE_KEY,parmasView.getBatch());

            if(conLineArr.length>1) {
                Integer line = Integer.valueOf(conLineArr[Integer.valueOf(batArr[1]) - 1]);
                if (parmasView.getScore() >= line) {
                    SystemParmas conLinePlusScoreParmas = getThresoldModel(parmasView.getProvince(), conlineKey, parmasView.getCategorie());
                    if (conLinePlusScoreParmas == null)
                        return false;
                    //批次线追加分
                    Integer plusScore = Integer.valueOf(conLinePlusScoreParmas.getConfigValue());
                    //用户分数小于(批次线+批次线追加分)
                    if (parmasView.getScore() <= Integer.valueOf(line) + plusScore) {
                        result = true;
                    }
                }
            }else {
                //用户分数大于 批次线
                if (parmasView.getScore() >= Integer.valueOf(conLineScore)) {
                    SystemParmas conLinePlusScoreParmas = getThresoldModel(parmasView.getProvince(), conlineKey, parmasView.getCategorie());
                    if (conLinePlusScoreParmas == null)
                        return false;
                    //批次线追加分
                    Integer plusScore = Integer.valueOf(conLinePlusScoreParmas.getConfigValue());
                    //用户分数小于(批次线+批次线追加分)
                    if (parmasView.getScore() <= Integer.valueOf(conLineScore) + plusScore)
                        result = true;
                }
            }
        }
        return result;
    }
    /**
     * 是否为推荐位次
     * @return
     */
    private boolean isRecommend(Integer btLine ,String batch,Integer logicTrend,Integer score,String provinceCode,Integer cate) {


        boolean result = false;

        String configKey = ReportUtil.getLinePlusByLogic(logicTrend, provinceCode);
        SystemParmas systemParmas = getThresoldModel(provinceCode, configKey, cate);
        if(systemParmas==null)
            return false;

        //是否为压线生
        result=isLine(logicTrend,batch,cate,provinceCode,score);
        Integer plusValue= Integer.valueOf(systemParmas.getConfigValue());



        return result;
    }

    /**
     * 是否为压线生
     * @return
     */
    private boolean isLine(Integer logic,String batch,Integer cate,String procode,Integer score) {
        boolean result = false;
        UniversityInfoParmasView universityInfoParmasView = new UniversityInfoParmasView();
        universityInfoParmasView.setBatch(batch);
        universityInfoParmasView.setCategorie(cate);
        universityInfoParmasView.setProvince(procode);
        universityInfoParmasView.setScore(score);
        ReportEnum.LogicTrend logicTrend = ReportEnum.LogicTrend.getLogic(logic);
        //线差
        if(logicTrend.equals(ReportEnum.LogicTrend.LINEDIFF)){
            result = isScoreSupplementaryLindDiff(universityInfoParmasView);
        } else { //位次
            result = isScoreSupplementary(universityInfoParmasView);
        }

        return result;
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
                        batchViews.add(batchConfig(c, btc, x, provinceCode, batchLine));
                    }
                }
            } else {
                Integer btLine = Integer.valueOf(batchLine);
                if (score >= btLine && btLine > 0) {
                    int batch = (i + 1);
                    batchViews.add(batchConfig(c, String.valueOf(batch), 0, provinceCode, batchLine));
                }
            }
        }
        return batchViews;
    }
    private BatchView batchConfig(Integer c,String viewBatch,Integer tagIndex ,String provinceCode,String batchLine) {
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
    public Integer getRankingRangeIndex(String proCode,Integer precedence,Integer majorType,String batch) {
        LOGGER.info("========获取排名规则区间下标 start=======");
        LOGGER.info("输入位次:" + precedence);
        String conlineKey=ReportUtil.getExecKey(proCode,ReportUtil.VOLUNTEER_BATCH_PRECEDENCE_KEY,batch);
        SystemParmas systemParmas = getThresoldModel(proCode,conlineKey ,majorType);
        if (systemParmas == null)
            return -1;
        LOGGER.info("组装排名VALUE:" + systemParmas.getConfigValue());
        Integer index = ReportUtil.getRankingRuleIndex(systemParmas.getConfigValue(), precedence);
        LOGGER.info("拆分取得下标:" + index);
        LOGGER.info("========获取排名规则区间下标 end=======");
        return index;
    }

    /**
     * 获取当前线差符合的线差规则区间下标   By 线差
     * @return
     */
    @Override
    public Integer getLineDiffRangeIndex(Integer lineDiff,String proCode,Integer majorType,String batch){
        LOGGER.info("========获取线差规则区间下标 start=======");
        LOGGER.info("输入线差:" + lineDiff);
        String key= ReportUtil.LINE_DIFF_RANGE_KEY + ReportUtil.ROLE_KEY_SPLIT_SYMBOL + batch;
        SystemParmas systemParmas = getThresoldModel(proCode, key,majorType);
        if(systemParmas==null)
            return -1;
        LOGGER.info("组装线差VALUE:" + systemParmas.getConfigValue());
        Integer index = ReportUtil.getRankingRuleIndex(systemParmas.getConfigValue(), lineDiff);
        LOGGER.info("========获取线差规则区间下标 start=======");
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

    /**
     * 算法逻辑走向
     * @param province
     * @param cate
     * @return
     */
    @Override
    public Integer getLogicTrend(String province,Integer cate) {
        LOGGER.info("========算法逻辑走向 start=======");
        LOGGER.info("province:" + province);
        LOGGER.info("cate:" + cate);
        SystemParmas systemParmas = getThresoldModel(province, ReportUtil.LOGIC_TREND, cate);
        if (systemParmas == null)
            return 0;
        Integer logic = Integer.valueOf(systemParmas.getConfigValue());
        LOGGER.info("logic:" + logic);
        LOGGER.info("========算法逻辑走向 start=======");
        return logic;
    }

    /**
     * 批次选择提示
     * @return
     */
    @Override
    public CheckBatchMsg checkBatchAlert(UniversityInfoParmasView paramView) {
        CheckBatchMsg checkBatchMsg = new CheckBatchMsg();

        Integer score= paramView.getScore()
                ,cate=paramView.getCategorie()
                ,logic=paramView.getLogic();
        String provinceCode=paramView.getProvince()
                    ,chkBatch=paramView.getBatch();


        SystemParmas systemParmas = selectSystemParmas(cate, provinceCode);

        //拆分批次线
        String[] batchArr = systemParmas.getConfigValue().split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);

        //线差
        Integer lineDiff=getLineDiff(chkBatch,score,cate,provinceCode);

//        String matchBatch=chkBatch;
        for (int i = 0; i < batchArr.length; i++) {
            String batchLine = batchArr[i];
            //拆分 A类 B类 批次
            String[] batchLineArr = batchLine.split("\\|");
            if (batchLineArr.length > 1) {  // >1 A B类 <=1 正常
                for (int x = 0; x < batchLineArr.length; x++) {
                    /***************************AB类后续处理**************************/
                }
            } else {
                Integer btLine = Integer.valueOf(batchLine);
                String batch = String.valueOf((i + 1));
                //批次线大于0 且分数大于等于批次线 表示用户分数已达标
                if (btLine > 0 && score >= btLine) {
                    //如果批次匹配          一本线 选择一本
                    if (batch.equals(chkBatch)) {
                        checkBatchMsg.setCheck(true);
                        checkBatchMsg.setMatch(batch);
                        checkBatchMsg.setSuggested(batch);
                    } else {
                        switch ((i + 1)) {
                            case 1:
                                switch (Integer.valueOf(chkBatch)) {
                                    case 2:
                                        boolean isNext = true;
                                        isNext = getSystemParamRange(provinceCode, cate, i + 1, ReportUtil.LINE_DIFF_CHK_BATCH_HREF, lineDiff);

                                        if (!isNext) {
                                            boolean result = getSystemParamRange(provinceCode, cate, i + 1, ReportUtil.LINE_DIFF_CHK_BATCH_VALID, lineDiff);
                                            if (result) {
                                                boolean existe = isExistedRange(provinceCode, cate, batch, lineDiff);
                                                if (existe) {
                                                    checkBatchMsg.setCheck(true);
                                                } else {
                                                    checkBatchMsg.setCheck(false);
                                                }
                                            }
                                        } else
                                            checkBatchMsg.setCheck(true);
                                        break;
                                    default:
                                        checkBatchMsg.setCheck(false);

                                }
                                break;
                            case 2:
                                switch (Integer.valueOf(chkBatch)) {
                                    case 3:
                                        boolean isNext = true;
                                        isNext = getSystemParamRange(provinceCode, cate, i + 1, ReportUtil.LINE_DIFF_CHK_BATCH_HREF, lineDiff);

                                        if (!isNext) {
                                            boolean result = getSystemParamRange(provinceCode, cate, i + 1, ReportUtil.LINE_DIFF_CHK_BATCH_VALID, lineDiff);
                                            if (result) {
                                                boolean existe = isExistedRange(provinceCode, cate, batch, lineDiff);
                                                if (existe) {
                                                    checkBatchMsg.setCheck(true);
                                                } else {
                                                    checkBatchMsg.setCheck(false);
                                                }
                                            }
                                        } else
                                            checkBatchMsg.setCheck(true);
                                        break;
                                    default:
                                        checkBatchMsg.setCheck(false);
                                }
                                break;
                            case 4:
                                break;
                            case 3:
                                checkBatchMsg.setCheck(false);
                                break;
                        }
                        checkBatchMsg.setMatch(batch);
                        checkBatchMsg.setSuggested(batch);
                    }

                    break;
                }
            }
        }
        return checkBatchMsg;
    }

    /**
     *
     * @param provinceCode 省份
     * @param cate   科类
     * @param batch  批次
     * @param key    KEY
     * @param tagetValue  对比值
     * @return
     */
    private boolean getSystemParamRange(String provinceCode,Integer cate,Integer batch,String key,Integer tagetValue) {
        SystemParmas systemPar = getThresoldModel(provinceCode, key, cate);

        if (systemPar == null)
            return true;

        String[] keyArr = systemPar.getConfigValue().split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);

        String[] searRange = keyArr[(batch-1)].split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);

        if (tagetValue > Integer.valueOf(searRange[0]) && tagetValue < Integer.valueOf(searRange[1]))
            return true;

        return false;
    }

    /**
     *   是否在范围内
     *   线差--跨批次线差是否在相应批次线差范围内
     *
     * @return
     */
    private boolean isExistedRange(String provinceCode,Integer cate,String batch,Integer tarValue) {

        String key = ReportUtil.getLineDiffRangeKey(provinceCode, batch);

        SystemParmas systemPar = getThresoldModel(provinceCode, key, cate);

        if (systemPar == null)
            return false;

        String[] rangeArr = systemPar.getConfigValue().split(ReportUtil.VOLUNTEER_KEY_SPLIT_SYMBOL);

        for (String range : rangeArr) {
            String[] lineDiff = range.split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);

            Integer start = Integer.valueOf(lineDiff[0]), end = Integer.valueOf(lineDiff[1]);

            if (tarValue >= start && tarValue <= end) {
                return true;
            }

        }

        return false;
    }

    /**
     * 根据分数获取批次
     * @param score
     * @param provinceCode
     * @param cate
     * @return
     */
    @Override
    public String getBatchByScore(Integer score,String provinceCode,Integer cate) {

        String b = null;

        SystemParmas systemParmas = selectSystemParmas(cate, provinceCode);

        //拆分批次线
        String[] batchArr = systemParmas.getConfigValue().split(ReportUtil.ROLE_VALUE_SPLIT_SYMBOL);

//        String matchBatch=chkBatch;
        for (int i = 0; i < batchArr.length; i++) {
            String batchLine = batchArr[i];
            //拆分 A类 B类 批次
            String[] batchLineArr = batchLine.split("\\|");
            if (batchLineArr.length > 1) {  // >1 A B类 <=1 正常
                for (int x = 0; x < batchLineArr.length; x++) {
                    /***************************AB类后续处理**************************/
                }
            } else {
                Integer btLine = Integer.valueOf(batchLine);
                String batch = String.valueOf((i + 1));
                //批次线大于0 且分数大于等于批次线 表示用户分数已达标
                if (btLine > 0 && score >= btLine) {
                    b = batch;
                    break;
                }
            }
        }
        return b;
    }
}
