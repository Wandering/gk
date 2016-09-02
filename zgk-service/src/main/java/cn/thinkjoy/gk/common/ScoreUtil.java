package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yangyongping on 16/8/2.
 */
@Component
public class ScoreUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreUtil.class);
    private static int RANDOM_NUM=10;

    private static int JS_AREA_CODE=320000;
    @Autowired
    private IScoreAnalysisService scoreAnalysisService;

    public String getYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        if(month>=7){
            return year+"";
        }else {
            return year-1+"";
        }
    }

    /**
     * 生成一分一段表表名
     * @param areaId
     * @param majorType
     * @return
     */
    public String getAreaTableName(long areaId,int majorType){
        String areaKey = scoreAnalysisService.queryAreaKey(areaId);
        return "zgk_data." + areaKey + "_" + majorType + "_y";
    }


    /**
     * 生成分数明细
     * @param map
     * @param majorType
     * @return
     */
    public Map<String,Object> getScores(Map<String,Object> map, Integer majorType){
        Map<String, Object> scores =  new LinkedHashMap();
        scores.put("语文", floatToStr(map.get("ywScore")) + "-" + floatToStr(map.get("ywScoreTotal")));
        scores.put("数学", floatToStr(map.get("sxScore")) + "-" + floatToStr(map.get("sxScoreTotal")));
        scores.put("外语", floatToStr(map.get("wyScore")) + "-" + floatToStr(map.get("wyScoreTotal")));
        if(majorType==null || (majorType!=1 &&majorType!=2)){
            if(map.containsKey("wlScore")) {
                scores.put("物理", floatToStr(map.get("wlScore")) + "-" + floatToStr(map.get("wlScoreTotal")));
            }
            if(map.containsKey("hxScore")) {
                scores.put("化学", floatToStr(map.get("hxScore")) + "-" + floatToStr(map.get("hxScoreTotal")));
            }
            if(map.containsKey("swScore")) {
                scores.put("生物", floatToStr(map.get("swScore")) + "-" + floatToStr(map.get("swScoreTotal")));
            }
            if(map.containsKey("zzScore")) {
                scores.put("思想政治", floatToStr(map.get("zzScore")) + "-" + floatToStr(map.get("zzScoreTotal")));
            }
            if(map.containsKey("lsScore")) {
                scores.put("历史", floatToStr(map.get("lsScore")) + "-" + floatToStr(map.get("lsScoreTotal")));
            }
            if(map.containsKey("dlScore")) {
                scores.put("地理", floatToStr(map.get("dlScore")) + "-" + floatToStr(map.get("dlScoreTotal")));
            }
            if(map.containsKey("tyScore")) {
                scores.put("通用技术", floatToStr(map.get("tyScore")) + "-" + floatToStr(map.get("tyScoreTotal")));
            }
        }else
        if (majorType == 2) {
            scores.put("物理", floatToStr(map.get("wlScore")) + "-" + floatToStr(map.get("wlScoreTotal")));
            scores.put("化学", floatToStr(map.get("hxScore")) + "-" + floatToStr(map.get("hxScoreTotal")));
            scores.put("生物", floatToStr(map.get("swScore")) + "-" + floatToStr(map.get("swScoreTotal")));

        } else if (majorType == 1) {
            scores.put("政治", floatToStr(map.get("zzScore")) + "-" + floatToStr(map.get("zzScoreTotal")));
            scores.put("历史", floatToStr(map.get("lsScore")) + "-" + floatToStr(map.get("lsScoreTotal")));
            scores.put("地理", floatToStr(map.get("dlScore")) + "-" + floatToStr(map.get("dlScoreTotal")));
        }
        return scores;
    }


    /**
     * 生成江苏分数明细
     * @param map
     * @param majorType
     * @return
     */
    public Map<String,Object> getScoresJS(Map<String,Object> map, int majorType){
        //删除额外字段,确保剩余都是分数字段
        Map<String,Object> map1 = new HashedMap();
        map1.putAll(map);

        List<String> removes = new ArrayList<>();
        removes.add("totalScore");
        Iterator<String> iterator = map1.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if(key.indexOf("Score")==-1){
                removes.add(key);
            }
        }
        for(String remove:removes) {
            map1.remove(remove);
        }

        //============================================



        Map<String, Object> scores =  new LinkedHashMap();
        scores.put("语文", floatToStr(map1.get("ywScore")) + "-" + floatToStr(map1.get("ywScoreTotal")));
        map1.remove("ywScore");
        map1.remove("ywScoreTotal");
        scores.put("数学", floatToStr(map1.get("sxScore")) + "-" + floatToStr(map1.get("sxScoreTotal")));
        map1.remove("sxScore");
        map1.remove("sxScoreTotal");
        scores.put("外语", floatToStr(map1.get("wyScore")) + "-" + floatToStr(map1.get("wyScoreTotal")));
        map1.remove("wyScore");
        map1.remove("wyScoreTotal");
        //分析当前分数的
//        map.get
        if (majorType == 2) {
            if(!map1.containsKey("wlScore")){
                throw new BizException("error","理科必须有物理");
            }
            scores.put("物理", scoreToTag(Float.valueOf(map1.get("wlScore").toString())));
            map1.remove("wlScore");
            map1.remove("wlScoreTotal");
        } else {
            if(!map1.containsKey("lsScore")){
                throw new BizException("error","文科必须有历史");
            }
            scores.put("历史", scoreToTag(Float.valueOf(map1.get("lsScore").toString())));
            map1.remove("lsScore");
            map1.remove("lsScoreTotal");
        }
        try {
            //一定会有一个值  如果没有说明入参有误 异常
            String key = map1.keySet().iterator().next();
            scores.put(SubjectEnum.valueOf(key.substring(0,2)).getSub(), scoreToTag(Float.valueOf(map1.get(key).toString())));
        }catch (Exception e){
                throw new BizException("error","上次添加值有误!");
            }
        return scores;
    }


    /**
     * 生成江苏分数明细
     * @param map
     * @param majorType
     * @return
     */
    public Map<String,Object> getScoresJS2(Map<String,Object> map, int majorType){
        //删除额外字段,确保剩余都是分数字段
        Map<String,Object> map1 = new HashedMap();
        map1.putAll(map);

        List<String> removes = new ArrayList<>();
        removes.add("totalScore");
        Iterator<String> iterator = map1.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if(key.indexOf("Score")==-1){
                removes.add(key);
            }
        }
        for(String remove:removes) {
            map1.remove(remove);
        }

        //============================================



        Map<String, Object> scores =  new LinkedHashMap();
        scores.put("语文", floatToStr(map1.get("ywScore")) + "-" + floatToStr(map1.get("ywScoreTotal")));
        map1.remove("ywScore");
        map1.remove("ywScoreTotal");
        scores.put("数学", floatToStr(map1.get("sxScore")) + "-" + floatToStr(map1.get("sxScoreTotal")));
        map1.remove("sxScore");
        map1.remove("sxScoreTotal");
        scores.put("外语", floatToStr(map1.get("wyScore")) + "-" + floatToStr(map1.get("wyScoreTotal")));
        map1.remove("wyScore");
        map1.remove("wyScoreTotal");
        //分析当前分数的
//        map.get
        if (majorType == 2) {
            if(!map1.containsKey("wlScore")){
                throw new BizException("error","理科必须有物理");
            }
            scores.put("物理", floatToStr(map1.get("wlScore")) + "-" + 120);
            map1.remove("wlScore");
            map1.remove("wlScoreTotal");
        } else {
            if(!map1.containsKey("lsScore")){
                throw new BizException("error","文科必须有历史");
            }
            scores.put("历史", floatToStr(map1.get("lsScore")) + "-" + 120);
            map1.remove("lsScore");
            map1.remove("lsScoreTotal");
        }
        try {
            //一定会有一个值  如果没有说明入参有误 异常
            String key = map1.keySet().iterator().next();
            scores.put(SubjectEnum.valueOf(key.substring(0,2)).getSub(), floatToStr(Float.valueOf(map1.get(key).toString()))+"-"+120);
        }catch (Exception e){
            throw new BizException("error","上次添加值有误!");
        }
        return scores;
    }



    /**
     * 获取批次线
     * @param batch
     * @param areaId
     * @param majorType
     * @return
     */
    public String getBatchScore(Integer batch,long areaId,int majorType){

        String scoreLine = scoreAnalysisService.queryScoreLine(areaId,majorType,getYear());


        String [] scoreStrs = scoreLine.split("-");

        String batchStr = batch.toString().substring(0,1);

        switch (Integer.parseInt(batchStr)){
            case 1:
                return scoreStrs[0].split("\\|")[0];
            case 2:
                return scoreStrs[1].split("\\|")[0];
            case 4:
                return scoreStrs[2].split("\\|")[0];
            case 8:
                return scoreStrs[3].split("\\|")[0];

        }

        return null;
    }

    /**
     * 获取批次线
     * @param areaId
     * @param majorType
     * @return
     */
    public Map<String,Object>  getBatchLine(long areaId,int majorType){
        Integer year = Integer.valueOf(getYear());

        /**
         * 获取最新批次线 当年没有获取次年
         */
        String scoreLine =null;
        do{
            scoreLine = scoreAnalysisService.queryScoreLine(areaId,majorType,year.toString());
            year--;
        }while (scoreLine==null);
        year+=1;

        String [] scoreStrs = scoreLine.split("-");
        Integer[][] scoreLines = new Integer[4][4];
        for(int i=0;i<4;i++){
            String[] scoreLineStr2=scoreStrs[i].split("\\|");
            for(int j=0;j<scoreLineStr2.length;j++) {
                scoreLines[i][j]=Integer.valueOf(scoreLineStr2[j]);
            }
        }
        Map<String,Object> rtnMap = new HashedMap();
        rtnMap.put("batchLine",scoreLines);
        rtnMap.put("year",year);
        return rtnMap;
    }


    /**
     * 获取最接近的上层批次线
     * @param areaId
     * @param majorType
     * @return
     */
    public Map<String,Object> getTopBatchLine(long areaId,int majorType,Float totalScore){

        if(totalScore==null){
            throw new BizException("error","成绩不能为空!");
        }
        Map<String,Object> scoreLinesMap = getBatchLine(areaId,majorType);
        Integer[][] scoreLines =null;
        scoreLines =(Integer[][]) scoreLinesMap.get("batchLine");
        // 当前有4层
        Float temp = null;
        String tempName = null;

        //规划计算当前分数最接近的上层分数
        for(int i=3;i>=0;i--){

            temp=scoreLines[i][0].floatValue();
            //出现为0跳过当前轮
            if(scoreLines[i][0]==0){
                continue;
            }
            if(totalScore-scoreLines[i][0]<0){
                break;
            }
            if(i==0&&totalScore-scoreLines[i][0]>0){
                temp=scoreLines[0][0].floatValue();
                //todo  这里目前写死为一二三高专批 当可以判断是专1还是专A时候更改
                switch (i){
                    case 3:
                        tempName="一批本科";
                        break;
                    case 2:
                        tempName="二批本科";
                        break;
                    case 1:
                        tempName="三批本科";
                        break;
                    case 0:
                        tempName="高职专科";
                        break;

                }
            }
        }
//        temp=scoreLines[0][0].floatValue();

        Map<String,Object> rtnMap = new HashedMap();
        rtnMap.put("score",floatToStr(temp));
        rtnMap.put("name",tempName);
        rtnMap.put("year",scoreLinesMap.get("year"));
        return rtnMap;
    }


    public Object[] getBatchAndScore(long areaId,int majorType,Float totalScore,String year){

        String scoreLine = scoreAnalysisService.queryScoreLine(areaId,majorType,year);

        String [] scoreStrs = scoreLine.split("-");
        Float bottomScore = null;
        Float lastScore=Float.parseFloat(scoreStrs[3].split("\\|")[0]);
        int i=1;
        for(String scoreStr:scoreStrs){
            float score=Float.parseFloat(scoreStr.split("\\|")[0]);
            if(totalScore-score>0F){
                bottomScore=score;
                break;
            }
            i++;
        }
        String batch2=null;
        switch (i){
            case 1:
                batch2="一批本科";
                break;
            case 2:
                batch2="二批本科";
                break;
            case 3:
                batch2="三批本科";
                break;
            case 4:
                batch2="高职高专";
                break;
            case 5:
                batch2="不足高职高专";
                bottomScore=lastScore;
                break;
        }

        Object[] objects = new Object[3];
        objects[0]=bottomScore;
        objects[1]=batch2;
        objects[2]=i;
        return objects;
    }

    public Float getLastBatchAndScore(long areaId,int majorType,int batch,String year){

        String scoreLine = scoreAnalysisService.queryScoreLine(areaId,majorType,year);

        String [] scoreStrs = scoreLine.split("-");
        Float bottomScore = null;
        String batch2=null;
        Float score=null;
        switch (batch){
            case 1:
                score=Float.parseFloat(scoreStrs[batch-1].split("\\|")[0]);
                break;
            case 2:
                score=Float.parseFloat(scoreStrs[batch-1].split("\\|")[0]);
                break;
            case 3:
                score=Float.parseFloat(scoreStrs[batch-1].split("\\|")[0]);
                //假如三批分数线为0滑落到下一批次
                if(score!=0)break;
            case 4:
                score=Float.parseFloat(scoreStrs[batch-1].split("\\|")[0]);

                break;
            case 5:
                break;
        }

        return score;
    }



    public String floatToStr(Object f){
        if(f==null){
            return "";
        }
        String[] strs= f.toString().split("\\.");
        if("0".equals(strs[1])){
            return strs[0];
        }
        return f.toString();
    }
    public Map<String,Object> getScores(HttpServletRequest request){
        Map<String, Object> scores = Maps.newHashMap();

        String prop = null;
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            if(prop.startsWith("scores")){
                String key = prop.substring(prop.lastIndexOf("scores[")+"scores[".length(),prop.lastIndexOf("]"));
                scores.put(SubjectEnum.valueOf(key).getSub(), request.getParameter(prop));
            }
        }

        return scores;
    }

    /**
     * 查询当前分数的分数等级
     * @return
     */
    public Integer getScoreRank(long areaId,int majorType,Float score){

        Map<String,Object> scoreLinesMap = getBatchLine(areaId,majorType);
        Integer[][] scoreLines=(Integer[][]) scoreLinesMap.get("batchLine");

        /**
         * 按照每个成绩等级第一个分数来计算
         */
        if(score - scoreLines[0][0]>50F){
            //一本+50
            return ScoreRankEnum.名垂校史.getSub();
        }else if(score-scoreLines[0][0]>0F){
            //一本+0-49
            return ScoreRankEnum.校刊红人.getSub();
        }else if(scoreLines[0][0]-score>0F && score - scoreLines[1][0]>0F){
            //二本以上 一本以下
            return ScoreRankEnum.三好学生.getSub();
        }else if(scoreLines[1][0]-score>0F && score - scoreLines[2][0]>0F){
            //三本以上 二本以下
            return ScoreRankEnum.教师常客.getSub();
        }else{
            //三本以下
            return ScoreRankEnum.逃课大军.getSub();
        }

    }

    /**
     * 查询当前分数的分数等级
     * @return
     */
    public Integer getScoreRankZJ(long areaId,Float score){

        Float totalScore =  750F;

        if(score - (totalScore*0.9F) >=0F){
            return ScoreRankEnum.名垂校史.getSub();
        }else if(score - (totalScore*0.8F) >=0F){
            return ScoreRankEnum.校刊红人.getSub();
        }else if(score - (totalScore*0.6F) >=0F){
            return ScoreRankEnum.三好学生.getSub();
        }else if(score - (totalScore*0.4F)  >=0F){
            return ScoreRankEnum.教师常客.getSub();
        }else{
            return ScoreRankEnum.逃课大军.getSub();
        }

    }

    /**
     * 查询当前分数的分数等级
     * @return
     */
    public Object[] getScoreWeak(Map<String,Object> scores,long areaId){
        String strongSubject=null;
        String weakSubject=null;
        Float initFloat1 = 1F;
        Float initFloat2 = 0F;
            Iterator<String> iterator = scores.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = (String) scores.get(key);
                Float scoreProportion=null;
                if(areaId==JS_AREA_CODE&&("政治".equals(key)||"历史".equals(key)||"地理".equals(key)||"物理".equals(key)||"化学".equals(key)||"生物".equals(key))){
                    scoreProportion = getProportionJS(value);
                }else {
                    scoreProportion = getProportion(value);
                }

                if (scoreProportion < initFloat1) {
                    initFloat1 = scoreProportion;
                    weakSubject = key;
                }

                if (scoreProportion > initFloat2 && scoreProportion <= 1F) {
                    initFloat2 = scoreProportion;
                    strongSubject = key;
                }
            }
            if (initFloat1 - initFloat2 < 0.05F && initFloat1 - initFloat2 > -0.05F) {
                weakSubject = strongSubject;
            }


        return new Object[]{getSubjectInfo(scores,strongSubject,areaId),getSubjectInfo(scores,weakSubject,areaId)};

    }

    public Float getProportion(String value){
        String[] scores = value.split("-");
        Float v1 = 2f;
        Float v2 = 0f;
        if(!"".equals(scores[0])){
            v1 =Float.valueOf(scores[0]);
        }
        if(!"".equals(scores[1])){
            v2 =Float.valueOf(scores[1]);
        }
        return v1/v2;

    }
    public Float getProportionJS(String value){
        if(value.indexOf("-")>0) {
            String[] values = value.split("-");
            value=values[0];
        }
        Float v1 =null;
        Float score = tagToScore(value);
        if(score==null) {
            v1=Float.valueOf(value);
        }
        return v1/120F;
    }


    /**
     * 分析成绩,给用户贴标签
     * @param scores
     * @return
     */
    public List<String> getUserLabel(Map<String,Object> scores,long areaId){

        List<String> labels = new ArrayList<>();
        if(scores==null){
            throw new BizException("error","分数集合为空!");
        }
        /**
         * 标签存放List
         */
        Set<String> temps = new HashSet<>();
        Float scoreSum=0F;
        Float totalScoreSum=0F;

        Iterator<String> iterator=scores.keySet().iterator();
        while (iterator.hasNext()){
            //子分数逻辑
            String key=iterator.next();
            String value = (String) scores.get(key);
            String[] strings =null;
//            if(areaId==JS_AREA_CODE&&("政治".equals(key)||"历史".equals(key)||"地理".equals(key)||"物理".equals(key)||"化学".equals(key)||"生物".equals(key))) {
//                strings = new String[]{tagToScore(value).toString(),"120"};
//            }else {
                strings = value.split("-");
//            }
            if(!"".equals(strings[0]) && !"".equals(strings[1])){
                Float subjectScore = Float.valueOf(strings[0]);
                Float subjectTotalScore = Float.valueOf(strings[1]);

                List<Integer> configs = new ArrayList<>();
                //科目标签
                configs.add(ScoreConfigEnum.valueOf(key).getSub());
                //通用标签
                configs.add(UserScoreType.LABEL_ALL);
                temps.addAll(getSubLabel(getScoreType(subjectScore,subjectTotalScore),configs));

                //抽取带有专业特色的标签
                configs=new ArrayList<>();
                configs.add(UserScoreType.LABEL_SUB_ALL);
                List<String> subAllLabel1s = null;

                subAllLabel1s = getSubLabel(getScoreType(subjectScore,subjectTotalScore),configs);

                if(subAllLabel1s!=null && subAllLabel1s.size()!=0){
                    List<String> subAllLabel2s=new ArrayList<>();
                    for(String sub:subAllLabel1s){
                        sub=sub.replace("_",key);
                        if(("语文".equals(key)||"数学".equals(key)||"外语".equals(key)||"通用技术".equals(key))&&sub.contains("#")){
                            continue;
                        }else {
                                if("政治".equals(key)||"历史".equals(key)||"地理".equals(key)){
                                    sub=sub.replace("#",UserScoreType.TYPE_WEN);
                                }
                                if("物理".equals(key)||"化学".equals(key)||"生物".equals(key)){
                                    sub=sub.replace("#",UserScoreType.TYPE_LI);
                                }


                        }
                        subAllLabel2s.add(sub);
                    }
                    temps.addAll(subAllLabel2s);
                }

                scoreSum+=subjectScore;
                totalScoreSum+=subjectTotalScore;
            }else {
                throw new BizException("error","科目分数格式不正确!");
            }
        }
        //总分逻辑
        List<Integer> configs = new ArrayList<>();
        configs.add(UserScoreType.LABEL_TOTAL);
        temps.addAll(getSubLabel(getScoreType(scoreSum,totalScoreSum),configs));
        labels.addAll(temps);
        //随机抽取10条返回
        return getRandomNum(labels,RANDOM_NUM);
    }


    /**
     * 取和分割标签
     * @param type
     * @param configs
     * @return
     */
    private List<String> getSubLabel(Integer type,List<Integer> configs){
        //从数据库提取标签
        List<String>  labelStrs = scoreAnalysisService.queryLabelByTypeAndConfig(type,configs);
        List<String> labels = new ArrayList<>();
        if(labelStrs!=null) {
            for (String labelStr : labelStrs) {
                for (String label : labelStr.split(",")) {
                    labels.add(label);
                }
            }
        }else {
            throw new BizException("error","没有合适的标签");
        }

        return labels;
    }

    /**
     * 判断分数等级  1-4级
     * @param score
     * @param totalScore
     * @return
     */
    private Integer getScoreType(Float score,Float totalScore){
        Float b = score/totalScore;
        if(b-0.8F>=0){
            return UserScoreType.FULL_MARK_80_100;
        }else if(b-0.6F>0){
            return UserScoreType.FULL_MARK_60_80;
        }else if(b-0.4F>0){
            return UserScoreType.FULL_MARK_40_60;
        }else{
            return UserScoreType.FULL_MARK_0_40;
        }
    }

    /**
     * 判断科目分数等级  1-4级
     * @param score
     * @param totalScore
     * @return
     */
    private Map<String,Object> getScoreType(Float score,Float totalScore,String subject){
        Float b = score/totalScore;
        Map<String,Object> map=new HashedMap();
        String desc=null;
        Integer tag=null;
        if(b-0.9F>=0){
            desc=UserScoreType.SUBJECT_DESC_1;
            tag=UserScoreType.SUBJECT_TAG_1;
        }else if(b-0.8F>0){
            desc=UserScoreType.SUBJECT_DESC_2;
            tag=UserScoreType.SUBJECT_TAG_2;
        }else if(b-0.6F>0){
            desc=UserScoreType.SUBJECT_DESC_3;
            tag=UserScoreType.SUBJECT_TAG_3;
        }else if(b-0.4F>0){
            desc=UserScoreType.SUBJECT_DESC_4;
            tag=UserScoreType.SUBJECT_TAG_4;
        }else{
            desc=UserScoreType.SUBJECT_DESC_5;
            tag=UserScoreType.SUBJECT_TAG_5;
        }
        map.put("subject",subject);
        map.put("desc",desc);
        map.put("tag",tag);
        return map;
    }

    /**
     * 返回随机数
     * @param list 备选号码
     * @param selected 备选数量
     * @return
     */
    private List<String> getRandomNum(List<String> list, int selected) {
        List<String> reList = new ArrayList<String>();
        List<String> cList=new ArrayList<>();
        cList.addAll(list);
        Random random = new Random();
        // 先抽取，备选数量的个数
        if (cList.size() >= selected) {
            for (int i = 0; i < selected; i++) {
                // 随机数的范围为0-list.size()-1;
                int target = random.nextInt(cList.size());
                reList.add(cList.get(target));
                cList.remove(target);
            }
        } else {
            selected = cList.size();
            for (int i = 0; i < selected; i++) {
                // 随机数的范围为0-list.size()-1;
                int target = random.nextInt(cList.size());
                reList.add(cList.get(target));
                cList.remove(target);
            }
        }

        return reList;
    }
    private Map<String,Object> getSubjectInfo(Map<String,Object> scores,String subject,long areaId){
        String[] strings=null;
//        if(areaId==JS_AREA_CODE&&("政治".equals(subject)||"历史".equals(subject)||"地理".equals(subject)||"物理".equals(subject)||"化学".equals(subject)||"生物".equals(subject))) {
//            strings = new String[]{tagToScore(scores.get(subject).toString()).toString(),"120"};
//        }else {
            strings = scores.get(subject).toString().split("-");
//        }
        if(!"".equals(strings[0]) && !"".equals(strings[1])){
            Float subjectScore = Float.valueOf(strings[0]);
            Float subjectTotalScore = Float.valueOf(strings[1]);
            return getScoreType(subjectScore,subjectTotalScore,subject);
        }else {
            throw new BizException("error","科目分数格式不正确!");
        }
    }


    /**
     * 量化等级对应成绩
     * @param f
     * @return
     */
    public String scoreToTag(Float f){

        switch (f.toString()){
            case "120.0":
                return "A+";
            case "100.0":
                return "A";
            case "80.0":
                return "B+";
            case "60.0":
                return "B";
            case "40.0":
                return "C";
            case "20.0":
                return "D";
        }
        return null;

//        if(f-120F==0){
//            return "A+";
//        }else if(f-100F==0F){
//            return "A";
//        }else if(f-80F==0F){
//            return "B+";
//        }else if(f-60F==0F){
//            return "B";
//        }else if(f-40F==0F){
//            return "C";
//        }else{
//            return "D";
//        }
    }

    /**
     * 转换成绩对应等级
     * @param s
     * @return
     */
    public Float tagToScore(String s){

//        if("A+".equals(s)){
//            return 120F;
//        }else if("A".equals(s)){
//            return 100F;
//        }else if("B+".equals(s)){
//            return 80F;
//        }else if("B".equals(s)){
//            return 60F;
//        }else if("C".equals(s)){
//            return 40F;
//        }else {
//            return 20F;
//        }

        switch (s){
            case "A+":
                return 120F;
            case "A":
                return 100F;
            case "B+":
                return 80F;
            case "B":
                return 60F;
            case "C":
                return 40F;
            case "D":
                return 20F;
        }
        return null;
    }

    public Map<String,Object> getScores2(HttpServletRequest request){
        Map<String, Object> scores = Maps.newLinkedHashMap();

        String prop = null;
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            prop = names.nextElement();
            if(prop.startsWith("scores")){
                String key = prop.substring(prop.lastIndexOf("scores[")+"scores[".length(),prop.lastIndexOf("]"));
                scores.put(key, request.getParameter(prop));
            }
        }

        return scores;
    }

    public static int getTagNum(String v1){
        if(v1.indexOf("另一门")>0){//物理A+,另一门B+
            String t1=v1.substring(2,v1.lastIndexOf(","));
            String t2=v1.substring(v1.lastIndexOf("另一门")+"另一门".length(),v1.length());
            v1=t1+t2;
        }
        Integer i=0;
        switch (v1){
            case "A+A+":
                i--;
            case "A+A":
                i--;
            case "AA":
                i--;
            case "A+B+":
                i--;
            case "AB+":
                i--;
            case "B+B+":
                i--;
            case "A+B":
                i--;
            case "AB":
                i--;
            case "B+B":
                i--;
            case "BB":
                i--;
            case "A+C":
                i--;
            case "AC":
                i--;
            case "B+C":
                i--;
            case "BC":
                i--;
            case "CC":
                i--;
            case "A+D":
                i--;
            case "AD":
                i--;
            case "B+D":
                i--;
            case "BD":
                i--;
            case "CD":
                i--;
            case "DD":
                i--;
            case "A+":
                i--;
            case "A":
                i--;
            case "B":
                i--;
            case "C":
                i--;
            case "D":
                i--;
            default:
                i--;

        }
        return i;
    }

    public List<Map<String,Object>> combineAlgorithm(String[] str){

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));


        List<Map<String,Object>> mapList=null;
        Map<String,Object> map=null;
        Map<String,Object> subjectItemMap=null;
        List<Map<String,Object>> lists=new ArrayList<>();
        for (int i = 1; i <= nBit; i++) {
            mapList=new ArrayList<>();
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    map=new HashMap<>();
                    map.put("selectSubject",str[j]);
                    mapList.add(map);
                }
            }
            subjectItemMap=new HashMap<>();
            subjectItemMap.put("subjectItem",mapList);
            lists.add(subjectItemMap);
        }
        return lists;
    }

    public String[] getZJUserScore(long userId){
        Map<String, Object> map = scoreAnalysisService.queryScoreRecordByUserId(userId);
        Map<String,Object> scores = (Map<String,Object>)map.get("scores");
        Iterator<String> keys = scores.keySet().iterator();
        //一定是三门成绩 否则异常
        String[] subjects =null;
        try {
            //提取科目
            subjects = new String[3];
            int i = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                if (!("语文".equals(key)) && (!"数学".equals(key)) && (!"外语".equals(key))) {
                    subjects[i++] = key;
                }
            }
        }catch (IndexOutOfBoundsException e){
            //数组越界的错误
            throw new BizException("error","当前科目不正确!");
        }
        return subjects;

    }


    public List<String> getLevelList(Map<String,Object> map,Integer majorType){

        String [] subs = getScoreLevel(map,majorType);


        return getScoreLevel(subs[1],subs[3],subs[0]);
    }

    private List<String> getScoreLevel(String v1,String v2,String sub){
        Set<String> levels = new HashSet<>();
        List<String> v1s=getScoreLevels(v1);
        List<String> v2s=getScoreLevels(v2);
        //遍历所有组合
        for(String v3:v1s){
            for(String v4:v1s) {
                if(tagToScore(v3)-tagToScore(v4)>0F) {
                    levels.add(v3 + v4);
                }else {
                    levels.add(v4 + v3);
                }
                levels.add(sub + v3 + ",另一门" + v4);
            }
        }
        List<String> list = new ArrayList<>();
        list.addAll(levels);
        return list;
    }
    public String[] getScoreLevel(Map<String,Object> scores,Integer majorType) {

        Iterator<String> keys = scores.keySet().iterator();
        //江苏一定是两门额外科目  否则抛异常
        Map<String, Object> map1 = new LinkedHashMap<>();

        while (keys.hasNext()) {
            String key = keys.next();
            if ((!"语文".equals(key)) && (!"数学".equals(key)) && (!"外语".equals(key))) {
                String value = scores.get(key).toString();
                map1.put(key, value);
            }
        }

        String sub = "历史";
        if (majorType == 2) {
            sub = "物理";
        }
        Map<String, Object> map2 = new HashedMap();
        map2.putAll(map1);
        String v1 = map2.get(sub).toString();
        String value1 =null;
        if (v1.indexOf("-")>0){
            value1 = scoreToTag(Float.valueOf(map2.get(sub).toString().split("-")[0]));
        }else {
            value1=v1;
        }
        map2.remove(sub);
        String key=map2.keySet().iterator().next();
        String v2 = map2.get(key).toString();
        String value2=null;
        if(v2.indexOf("-")>0){
            value2 = scoreToTag(Float.valueOf(map2.get(key).toString().split("-")[0]));
        }else {
            value2=v2;
        }
        return new String[]{sub,value1,key,value2};
    }



    private List<String> getScoreLevels(String v1){
        List<String> list= new ArrayList<>();
        //遍历所有组合
        switch (v1){
            case "A+":
                list.add("A+");
            case "A":
                list.add("A");
            case "B+":
                list.add("B+");
            case "B":
                list.add("B");
            case "C":
                list.add("C");
            case "D":
                list.add("D");
        }
        return list;
    }

}
