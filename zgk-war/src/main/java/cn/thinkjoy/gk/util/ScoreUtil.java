package cn.thinkjoy.gk.util;

import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;

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
        Map<String, Object> scores = new HashedMap();
        scores.put("语文", floatToStr((float) map.get("ywScore")) + "-" + floatToStr((float) map.get("ywScoreTotal")));
        scores.put("数学", floatToStr((float) map.get("sxScore")) + "-" + floatToStr((float) map.get("sxScoreTotal")));
        scores.put("外语", floatToStr((float) map.get("wyScore")) + "-" + floatToStr((float) map.get("wyScoreTotal")));

        if (majorType == 2) {
            scores.put("物理", floatToStr((float) map.get("wlScore")) + "-" + floatToStr((float) map.get("wlScoreTotal")));
            scores.put("化学", floatToStr((float) map.get("hxScore")) + "-" + floatToStr((float) map.get("hxScoreTotal")));
            scores.put("生物", floatToStr((float) map.get("swScore")) + "-" + floatToStr((float) map.get("swScoreTotal")));

        } else if ((majorType == 1)) {
            scores.put("历史", floatToStr((float) map.get("lsScore")) + "-" + floatToStr((float) map.get("lsScoreTotal")));
            scores.put("政治", floatToStr((float) map.get("zzScore")) + "-" + floatToStr((float) map.get("zzScoreTotal")));
            scores.put("地理", floatToStr((float) map.get("dlScore")) + "-" + floatToStr((float) map.get("dlScoreTotal")));
        } else {
            scores.put("物理", floatToStr((float) map.get("wlScore")) + "-" + floatToStr((float) map.get("wlScoreTotal")));
            scores.put("化学", floatToStr((float) map.get("hxScore")) + "-" + floatToStr((float) map.get("hxScoreTotal")));
            scores.put("生物", floatToStr((float) map.get("swScore")) + "-" + floatToStr((float) map.get("swScoreTotal")));
            scores.put("历史", floatToStr((float) map.get("lsScore")) + "-" + floatToStr((float) map.get("lsScoreTotal")));
            scores.put("思想政治", floatToStr((float) map.get("zzScore")) + "-" + floatToStr((float) map.get("zzScoreTotal")));
            scores.put("地理", floatToStr((float) map.get("dlScore")) + "-" + floatToStr((float) map.get("dlScoreTotal")));
            scores.put("通用技术", floatToStr((float) map.get("tyScore")) + "-" + floatToStr((float) map.get("tyScoreTotal")));
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

    public Object[] getBatchAndScore(long areaId,int majorType,Float totalScore,String year){

        String scoreLine = scoreAnalysisDAO.queryScoreLine(areaId,majorType,year);

        String [] scoreStrs = scoreLine.split("-");
        Float bottomScore = null;
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
    public String floatToStr(Float f){
        if(f==null){
            return null;
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
}
