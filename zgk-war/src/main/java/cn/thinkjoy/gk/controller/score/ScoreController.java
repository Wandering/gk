package cn.thinkjoy.gk.controller.score;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by yangyongping on 16/7/26.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/score")
public class ScoreController {


    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     * @return
     */
    @RequestMapping(value = "/queryScoreRecordByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryScoreRecordByUserId(long userId,int source){

        Map<String,Object> resultMap = new HashedMap();
        resultMap.put("areaName","陕西省");
        resultMap.put("majorType","2");
        Map<String,Object> scores = new HashedMap();
        scores.put("语文","90-100");
        scores.put("数学","90-100");
        scores.put("外语","90-100");
        scores.put("物理","90-100");
        scores.put("化学","90-100");
        scores.put("生物","90-100");
        resultMap.put("scores",scores);
        resultMap.put("areaName","陕西省");
        return resultMap;
    }



    /**
     * 用户填写完分数信息后，提交成绩信息
     * @return
     */
    @RequestMapping(value = "/insertScoreRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object insertScoreRecord(long userId,
                                    int source,
                                    long areaId,
                                    int majorType,
                                    Map<String,Object> scores){

        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("recordId",1);
        return resultMap;
    }


    /**
     * 用户根据记录ID查询分数的信息和排名信息
     * @return
     */
    @RequestMapping(value = "/queryInfoByRecordId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryInfoByRecordId(long recordId){

        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("totalScore",600);
        resultMap.put("majorType",1);
        resultMap.put("proviceRank",1000);
        resultMap.put("stuNum",50);
        Map<String,Object> scores = new HashedMap();
        scores.put("语文","90-100");
        scores.put("数学","90-100");
        scores.put("外语","90-100");
        scores.put("物理","90-100");
        scores.put("化学","90-100");
        scores.put("生物","90-100");
        resultMap.put("scores",scores);
        return resultMap;
    }

    /**
     * 根据用户Id和用户来源查询用户所有的提交记录
     * @return
     */
    @RequestMapping(value = "/queryAllRecordByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryAllRecordByUserId(long userId,int source){

        List<Map<String,Object>> list = new ArrayList<>();

        for(int i=0;i<5;i++) {
            Map<String, Object> resultMap = new HashedMap();
            resultMap.put("recordId", 1);
            resultMap.put("totalScore", 600);
            resultMap.put("majorType", 1);
            resultMap.put("proviceRank", 1000);
            resultMap.put("cdate", 146951915700l);
            Map<String, Object> scores = new HashedMap();
            scores.put("语文", "90-100");
            scores.put("数学", "90-100");
            scores.put("外语", "90-100");
            scores.put("物理", "90-100");
            scores.put("化学", "90-100");
            scores.put("生物", "90-100");
            resultMap.put("scores", scores);
            list.add(resultMap);
        }
        return list;
    }

    /**
     * 根据省份ID和用户总成绩查询该省份的相应批次线
     * @return
     */
    @RequestMapping(value = "/queryBatchLineByAreaId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryBatchLineByAreaId(float totalScore,long areaId){
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put("topLine", 430);
        resultMap.put("bottomLine", 380);
        return resultMap;
    }

    /**
     * 根据用户总分为用户推荐院校
     * @return
     */
    @RequestMapping(value = "/recommendSchool",method = RequestMethod.GET)
    @ResponseBody
    public Object recommendSchool(float totalScore,long areaId){

        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("schoolName","北京大学");
        resultMap.put("batch","一批本科");
        resultMap.put("stuNum",100);
        resultMap.put("averageScore",600.0);
        resultMap.put("gapSchool",-20);
        return resultMap;
    }

    /**
     * 根据大学ID和省份ID查询相应的录取批次
     * @return
     */
    @RequestMapping(value = "/queryBatchsBySchoolIdAndAreaId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryBatchsBySchoolIdAndAreaId(long areaId,long schoolId){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> resultMap1=new HashedMap();
        resultMap1.put("batchId",1);
        resultMap1.put("batchName","一批本科");
        Map<String,Object> resultMap2=new HashedMap();
        resultMap2.put("batchId",2);
        resultMap2.put("batchName","二批本科");
        Map<String,Object> resultMap3=new HashedMap();
        resultMap3.put("batchId",4);
        resultMap3.put("batchName","三批本科");
        Map<String,Object> resultMap4=new HashedMap();
        resultMap4.put("batchId",8);
        resultMap4.put("batchName","高职专科");
        list.add(resultMap1);
        list.add(resultMap2);
        list.add(resultMap3);
        list.add(resultMap4);
        return list;
    }

    /**
     * 根据用户总分、学校ID、批次信息查询用户与目标院校距离
     * @return
     */
    @RequestMapping(value = "/queryGapBySchoolIdAndBatch",method = RequestMethod.GET)
    @ResponseBody
    public Object queryGapBySchoolIdAndBatch(float totalScore,long schoolId,int batch,long areaId){

        return null;
    }

}
