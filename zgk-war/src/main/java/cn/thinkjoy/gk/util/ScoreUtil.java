package cn.thinkjoy.gk.util;

import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yangyongping on 16/8/2.
 */
@Component
public class ScoreUtil {

    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;

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
        String areaKey = scoreAnalysisDAO.queryAreaKey(areaId);
        return "zgk_data." + areaKey + "_" + majorType + "_y";
    }


    /**
     * 生成分数明细
     * @param map
     * @param majorType
     * @return
     */
    public Map<String,Object> getScores(Map<String,Object> map, int majorType){
        Map<String, Object> scores =  new LinkedHashMap();
        scores.put("语文", floatToStr(map.get("ywScore")) + "-" + floatToStr(map.get("ywScoreTotal")));
        scores.put("数学", floatToStr(map.get("sxScore")) + "-" + floatToStr(map.get("sxScoreTotal")));
        scores.put("外语", floatToStr(map.get("wyScore")) + "-" + floatToStr(map.get("wyScoreTotal")));

        if (majorType == 2) {
            scores.put("物理", floatToStr(map.get("wlScore")) + "-" + floatToStr(map.get("wlScoreTotal")));
            scores.put("化学", floatToStr(map.get("hxScore")) + "-" + floatToStr(map.get("hxScoreTotal")));
            scores.put("生物", floatToStr(map.get("swScore")) + "-" + floatToStr(map.get("swScoreTotal")));

        } else if ((majorType == 1)) {
            scores.put("政治", floatToStr(map.get("zzScore")) + "-" + floatToStr(map.get("zzScoreTotal")));
            scores.put("历史", floatToStr(map.get("lsScore")) + "-" + floatToStr(map.get("lsScoreTotal")));
            scores.put("地理", floatToStr(map.get("dlScore")) + "-" + floatToStr(map.get("dlScoreTotal")));
        } else {
            scores.put("物理", floatToStr(map.get("wlScore")) + "-" + floatToStr(map.get("wlScoreTotal")));
            scores.put("化学", floatToStr(map.get("hxScore")) + "-" + floatToStr(map.get("hxScoreTotal")));
            scores.put("生物", floatToStr(map.get("swScore")) + "-" + floatToStr(map.get("swScoreTotal")));
            scores.put("思想政治", floatToStr(map.get("zzScore")) + "-" + floatToStr(map.get("zzScoreTotal")));
            scores.put("历史", floatToStr(map.get("lsScore")) + "-" + floatToStr(map.get("lsScoreTotal")));
            scores.put("地理", floatToStr(map.get("dlScore")) + "-" + floatToStr(map.get("dlScoreTotal")));
            scores.put("通用技术", floatToStr(map.get("tyScore")) + "-" + floatToStr(map.get("tyScoreTotal")));
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

        String scoreLine = scoreAnalysisDAO.queryScoreLine(areaId,majorType,getYear());


        String [] scoreStrs = scoreLine.split("-");

        String batchStr = batch.toString().substring(0,1);

        switch (Integer.parseInt(batchStr)){
            case 1:
                return scoreStrs[0].split("\\|")[0];
            case 2:
                return scoreStrs[1].split("\\|")[0];
            case 4:
                return scoreStrs[3].split("\\|")[0];
            case 8:
                return scoreStrs[4].split("\\|")[0];

        }

        return null;
    }

    /**
     * 获取批次线
     * @param areaId
     * @param majorType
     * @return
     */
    public Integer[] getBatchLine(long areaId,int majorType){
        Integer year = Integer.valueOf(getYear());

        String scoreLine =null;
        do{
            scoreLine = scoreAnalysisDAO.queryScoreLine(areaId,majorType,year.toString());
            year--;
        }while (scoreLine==null);


        String [] scoreStrs = scoreLine.split("-");
        Integer [] scoreLines = new Integer[4];
        for(int i=0;i<4;i++){
            scoreLines[i]=Integer.valueOf(scoreStrs[i].split("\\|")[0]);
        }
        return scoreLines;
    }

    public Object[] getBatchAndScore(long areaId,int majorType,Float totalScore,String year){

        String scoreLine = scoreAnalysisDAO.queryScoreLine(areaId,majorType,year);

        String [] scoreStrs = scoreLine.split("-");
        Float bottomScore = null;
        Float lastScore=Float.parseFloat(scoreStrs[3].split("\\|")[0]);
        int i=1;
        for(String scoreStr:scoreStrs){
            float score=Float.parseFloat(scoreStr.split("\\|")[0]);
            if(totalScore-score>0){
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

        String scoreLine = scoreAnalysisDAO.queryScoreLine(areaId,majorType,year);

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

                break;
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

        Integer[] scoreLines = getBatchLine(areaId,majorType);

            if(score - scoreLines[0]>50){
                //一本+50
                return ScoreRankEnum.名垂校史.getSub();
            }else if(score-scoreLines[0]>0){
                //一本+0-49
                return ScoreRankEnum.校刊红人.getSub();
            }else if(scoreLines[0]-score>0 && score - scoreLines[1]>0){
                //二本以上 一本以下
                return ScoreRankEnum.三好学生.getSub();
            }else if(scoreLines[1]-score>0 && score - scoreLines[2]>0){
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
    public String[] getScoreWeak(Map<String,Object> scores){
        String strongSubject=null;
        String weakSubject=null;
        Float initFloat1 = 1F;
        Float initFloat2 = 0F;
        Iterator<String> iterator =  scores.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = (String) scores.get(key);
            Float scoreProportion = getProportion(value);
            if(scoreProportion<initFloat1){
                initFloat1=scoreProportion;
                weakSubject = key;
            };
            if(scoreProportion>initFloat2 && scoreProportion<=1F){
                initFloat2=scoreProportion;
                strongSubject = key;
            };

        }
        if(initFloat1-initFloat2<0.05 && initFloat1-initFloat2>-0.05){
            weakSubject=strongSubject;
        }
        return new String[]{strongSubject,weakSubject};

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

}
