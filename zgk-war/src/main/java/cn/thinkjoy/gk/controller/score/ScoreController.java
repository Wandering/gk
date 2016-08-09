package cn.thinkjoy.gk.controller.score;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import cn.thinkjoy.gk.util.ScoreUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by yangyongping on 16/7/26.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/score")
public class ScoreController {

    @Autowired
    private IScoreAnalysisService scoreAnalysisService;
    @Autowired
    private ScoreUtil scoreUtil;


    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     * @return
     */
    @RequestMapping(value = "/queryScoreRecordByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryScoreRecordByUserId(@RequestParam long userId){
        Map<String,Object> map = scoreAnalysisService.queryScoreRecordByUserId(userId);
        if(map==null){
            return new HashedMap();
        }
        Map<String,Object> resultMap = new HashedMap();
        resultMap.put("areaName",map.get("areaName"));
        Integer majorType=(Integer) map.get("majorType");
        resultMap.put("majorType",majorType);
        resultMap.put("schoolName",map.get("schoolName"));
        Map<String,Object> scores = scoreUtil.getScores(map,majorType);
        resultMap.put("scores",scores);
        return resultMap;
    }

    /**
     * 用保存用户信息
     * @return
     */
    @RequestMapping(value = "/insertUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object insertUserInfo(@RequestParam long userId,
                                    @RequestParam long provinceId,
                                    @RequestParam long cityId,
                                    @RequestParam long countyId,
                                    @RequestParam long schoolCode,
                                    @RequestParam String schoolName,
                                    @RequestParam String gradeInfo,
                                    @RequestParam String classInfo){
        //保存用户信息

        Map<String,Object> insertMap = new HashedMap();
        insertMap.put("userId",userId);
        insertMap.put("provinceId",provinceId);
        insertMap.put("cityId",cityId);
        insertMap.put("countyId",countyId);
        insertMap.put("schoolCode",schoolCode);
        insertMap.put("schoolName",schoolName);
        insertMap.put("gradeInfo",gradeInfo);
        insertMap.put("classInfo",classInfo);
        try {
            int uu = scoreAnalysisService.setUserInfo(insertMap);
            if(uu==0){
                throw new BizException("error","添加失败,用户不存在!");
            }
        }catch (BizException e){
            throw e;
        }
        return true;
    }

    /**
     * 根据区Id获取当前区高中
     * @return
     */
    @RequestMapping(value = "/queryHighSchoolByCountyId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryHighSchoolByCountyId(@RequestParam long countyId,@RequestParam String schoolName){
        if("".equals(schoolName)){
            schoolName=null;
        }
        return scoreAnalysisService.queryHighSchoolByCountyId(countyId,schoolName);
    }

    /**
     * 查询用户信息
     * @return
     */
    @RequestMapping(value = "/queryUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public Object queryUserInfo(@RequestParam long userId){
        return scoreAnalysisService.queryUserInfo(userId);
    }

    /**
     * 用户填写完分数信息后，提交成绩信息
     * @return
     */
    @RequestMapping(value = "/insertScoreRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object insertScoreRecord(@RequestParam long userId,
                                    @RequestParam long areaId,
                                    @RequestParam Integer majorType,
                                    HttpServletRequest request){
        //获取成绩
        Map<String, Object> scores = scoreUtil.getScores(request);


        Map<String,Object> insertMap = new HashedMap();
        insertMap.put("userId",userId);
        insertMap.put("areaId",areaId);
        insertMap.put("majorType",majorType);
        insertMap.put("cdate",System.currentTimeMillis());
        Map<String,Object> insertScores = new HashedMap();

        Iterator iterator=scores.keySet().iterator();
        Float totalScore=0f;
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            String value = (String) scores.get(key);
            String[] values = value.split("-");
            totalScore+=Float.valueOf(values[0]);
            insertScores.put(key+"Score",values[0]);
            insertScores.put(key+"ScoreTotal",values[1]);
        }
        if(insertScores.size()!=12&&insertScores.size()!=14){
            throw new BizException("error","提交科目不完整!");
        }

        insertMap.put("scores",insertScores);
        insertMap.put("totalScore",totalScore);
        scoreAnalysisService.insertScoreRecord(insertMap);
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("recordId",insertMap.get("recordId"));
        return resultMap;
    }


    /**
     * 用户根据记录ID查询分数的信息和排名信息
     * @return
     */
    @RequestMapping(value = "/queryInfoByRecordId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryInfoByRecordId(@RequestParam long recordId){

        Map<String,Object> map = scoreAnalysisService.queryInfoByRecordId(recordId);
        if(map==null){
            return new HashedMap();
        }
        Map<String,Object> resultMap = new HashedMap();

        Float totalScore = (Float) map.get("totalScore");
        Long areaId = Long.valueOf(map.get("areaId").toString()) ;

        resultMap.put("totalScore",totalScore);
        resultMap.put("areaName",map.get("areaName"));
        Integer majorType=(Integer) map.get("majorType");
        resultMap.put("majorType",majorType);
        resultMap.put("scores",scoreUtil.getScores(map,majorType));
        String areaTableName = scoreUtil.getAreaTableName(areaId, majorType);
        //文或者理科总人数
        Integer allStuNum = scoreAnalysisService.queryAllAreaStuNum(areaTableName);

        //极端情况
        if(scoreAnalysisService.isExistMaxScore(totalScore,areaTableName)){
            //当前分数超过了一分一段表的最大值 或者  达到很高的值
            resultMap.put("proviceRank", -1);
            resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));

        }else if(scoreAnalysisService.isExistScore(totalScore,areaTableName)) {
            //            正常情况
            //需要超过多少人
            //一分超过多少人
            Integer stuNum = scoreAnalysisService.queryStuNum(totalScore, areaTableName);
            //全省排名
            Integer proviceRank = scoreAnalysisService.queryProviceRank(totalScore, areaTableName);

            resultMap.put("stuNum", stuNum);
            String[] nums = String.valueOf(100 - ((Float.valueOf(proviceRank) / Float.valueOf(allStuNum)) * 100)).split("\\.");
            String proviceRankPro = nums[0] + "." + nums[1].substring(0, 2) + "%";
            resultMap.put("proviceRankPro", proviceRankPro);
            resultMap.put("proviceRank", proviceRank);

            resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));
        }else {
            //           分数不在一分一段中的情况
            //文或者理科总人数
            resultMap.put("proviceRank", -allStuNum);
            resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));
        }
        return resultMap;
    }

    /**
     * 根据用户Id和用户来源查询用户所有的提交记录
     * @return
     */
    @RequestMapping(value = "/queryAllRecordByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryAllRecordByUserId(@RequestParam long userId){


        List<Map<String,Object>> list = new ArrayList<>();

        List<Map<String,Object>> queryList = scoreAnalysisService.queryAllRecordByUserId(userId);
        if(queryList!=null && (!queryList.isEmpty())) {
            for (Map<String, Object> map : queryList) {
                Map<String, Object> resultMap = new HashedMap();
                Float totalScore = (Float) map.get("totalScore");
                Long areaId = Long.valueOf(map.get("areaId").toString()) ;
                resultMap.put("recordId", map.get("recordId"));
                resultMap.put("totalScore", totalScore);
                resultMap.put("areaName", map.get("areaName"));
                Integer majorType = (Integer) map.get("majorType");
                resultMap.put("majorType", majorType);
                resultMap.put("cdate", map.get("cdate"));
                Map<String,Object> scores =  scoreUtil.getScores(map,majorType);

                resultMap.put("scores",scores);
                String areaTableName = scoreUtil.getAreaTableName(areaId,majorType);
                //分析科目强弱
                String [] subjects = scoreUtil.getScoreWeak(scores);
                resultMap.put("strong", subjects[0]);
                resultMap.put("weak", subjects[1]);



                //极端情况
                if(scoreAnalysisService.isExistMaxScore(totalScore,areaTableName)){
                    //当前分数超过了一分一段表的最大值 或者  达到很高的值
                    resultMap.put("proviceRank", -1);
                    resultMap.put("scoreRank", scoreUtil.getScoreRank(areaId, majorType, totalScore));

                }if(scoreAnalysisService.isExistScore(totalScore,areaTableName)) {
                    //需要超过多少人
                    Integer stuNum = scoreAnalysisService.queryStuNum(totalScore, areaTableName);
                    Float totalScore1=totalScore;
                    while (stuNum==null){
                        stuNum = scoreAnalysisService.queryStuNum(totalScore1--, areaTableName);
                    }
                    Integer proviceRank = scoreAnalysisService.queryProviceRank(totalScore1, areaTableName);
                    resultMap.put("stuNum", stuNum);
                    resultMap.put("proviceRank", proviceRank);
                }else {
                    //TODO           分数不在一分一段中的情况
                    //文或者理科总人数
                    int allStuNum = scoreAnalysisService.queryAllAreaStuNum(areaTableName);
                    resultMap.put("proviceRank", -allStuNum);
                }


                list.add(resultMap);
            }
        }

        return list;
    }

    /**
     * 根据省份ID和用户总成绩查询该省份的相应批次线
     * @return
     */
    @RequestMapping(value = "/queryBatchLineByAreaId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryBatchLineByAreaId(@RequestParam float totalScore,
                                         @RequestParam long areaId,
                                         @RequestParam int majorType){

        String scoreLine = scoreAnalysisService.queryScoreLine(areaId,majorType,scoreUtil.getYear());

        String [] scoreStrs = scoreLine.split("-");
        Float topScore = null;
        Float bottomScore = null;
        int i=1;
        for(String scoreStr:scoreStrs){
            float score=Float.parseFloat(scoreStr.split("\\|")[0]);
            if(totalScore-score<0){
                topScore=score;
            }else {
                bottomScore=score;
                break;
            }
            i++;
        }
        String batch1=null;
        String batch2=null;
        switch (i){
            case 1:
                batch2="一批本科";
                break;
            case 2:
                batch1="一批本科";
                batch2="二批本科";
                break;
            case 3:
                batch1="二批本科";
                batch2="三批本科";
                break;
            case 4:
                batch1="三批本科";
                batch2="高职高专";
                break;
            case 5:
                batch1="高职高专";
                break;
        }

        Map<String, Object> resultMap = new HashedMap();
        Map<String, Object> topLine = new HashedMap();
        Map<String, Object> bottomLine = new HashedMap();
        topLine.put("batch", batch1);
        topLine.put("score", topScore);
        bottomLine.put("batch", batch2);
        bottomLine.put("score", bottomScore);
        if(bottomScore!=null&&bottomScore==0) {
            bottomLine.put("batch", null);
            bottomLine.put("score", null);
        }
        resultMap.put("topLine", topLine);
        resultMap.put("bottomLine", bottomLine);
        return resultMap;
    }

    /**
     * 根据用户总分为用户推荐院校
     * @return
     */
    @RequestMapping(value = "/recommendSchool",method = RequestMethod.GET)
    @ResponseBody
    public Object recommendSchool(@RequestParam float totalScore,@RequestParam long areaId,@RequestParam int majorType){

        Integer lastYear = Integer.valueOf(scoreUtil.getYear())-1;

        //确定当前分数对应当年批次分数
//        long areaId,int majorType,Float totalScore,String year
        Object[] line1s = scoreUtil.getBatchAndScore(areaId,majorType,totalScore,scoreUtil.getYear());
        if(line1s[2]==5){
            //todo 假如不足高职专科批次(分数超低)

            //推荐10所高职院校
            return scoreAnalysisService.queryLowstUniversity(areaId,majorType,totalScore,lastYear.toString());
        }
        int batch= (int)line1s[2];
        //获得分差1  考生分-16年分数线
        float difference = totalScore-(Float) line1s[0];
        //确定点钱分数对应次年批次分数


        Float line2 = scoreUtil.getLastBatchAndScore(areaId,majorType,batch,lastYear.toString());

        //获得分差2  院校15年分-15年分数线 (15年分数线)



        //计算公式为 lowestScore - line -  difference > = bc  || lowestScore - line -  difference > = -bc

        int count =0;
        int bc = 0;
        do {
            count = scoreAnalysisService.countUniversity(areaId,(Integer)line1s[2],majorType,lastYear.toString(),difference,line2,bc);
            //增加步长
            bc+=5;
        }while (count<20&&bc<750);

        bc-=5;
        //返回前20个院校
        List<Map<String,Object>> resultList = scoreAnalysisService.queryUniversityByScore(areaId,(Integer)line1s[2],majorType,lastYear.toString(),difference,line2,totalScore,bc);


        return resultList;
    }

    /**
     * 根据大学ID和省份ID查询相应的录取批次
     * @return
     */
    @RequestMapping(value = "/queryBatchsBySchoolIdAndAreaId",method = RequestMethod.GET)
    @ResponseBody

    public Object queryBatchsBySchoolIdAndAreaId(@RequestParam long areaId,
                                                 @RequestParam long schoolId,
                                                 @RequestParam Integer majorType){


        List<Map<String,Object>> list = null;

        Integer year = Integer.valueOf(scoreUtil.getYear());

        list=scoreAnalysisService.queryUnivsersityBatch(areaId,schoolId,year.toString(),majorType);
        //尝试获取最新的年份对应的录取批次,获取不到获取次年的录取批次
        if(list==null||list.size()==0){
            list=scoreAnalysisService.queryUnivsersityBatch(areaId,schoolId,(year-1)+"",majorType);
        }
        return list;
    }

    /**
     * 根据用户总分、学校ID、批次信息查询用户与目标院校距离
     * @return
     */
    @RequestMapping(value = "/queryGapBySchoolIdAndBatch",method = RequestMethod.POST)
    @ResponseBody
    public Object queryGapBySchoolIdAndBatch(@RequestParam long recordId,
                                             Long schoolId,
                                             Integer batch,
                                             @RequestParam long userId){

        Map<String,Object> map = scoreAnalysisService.queryInfoByRecordId(recordId);
        //假如院校没有传入 默认为使用上次院校
        if(schoolId!=null && batch!=null){
            Map<String,Object> insertMap = new HashedMap();
            insertMap.put("userId",userId);
            insertMap.put("areaId",map.get("areaId"));
            insertMap.put("universityId",schoolId);
            insertMap.put("batch",batch);
            insertMap.put("cdate",System.currentTimeMillis());
            scoreAnalysisService.insertTarget(insertMap);
        }else {
            //获取上次测评院校和批次
            Map<String,Object> targetMap = scoreAnalysisService.queryLastTarget(userId);
            schoolId=Long.valueOf(targetMap.get("universityId").toString());
            batch=Integer.valueOf(targetMap.get("batch").toString());
        }

        long areaId = Long.valueOf(map.get("areaId").toString());
        int majorType = (int)map.get("majorType");
        Float totalScore=(Float) map.get("totalScore");
        String areaTableName = scoreUtil.getAreaTableName(areaId,majorType);
        String year=(Integer.valueOf(scoreUtil.getYear())-1)+"";
        String name = scoreAnalysisService.querySchoolNameById(schoolId);
        String batchName = scoreAnalysisService.queryBatchNameById(batch);
        Map<String,Object> schoolLineMap = scoreAnalysisService.queryUnivsersityLowestScore(schoolId,areaId,batch,majorType,year);
        Float schoolLine=null;
        String schoolLineYear=null;
        if(schoolLineMap!=null){
            schoolLine=Float.valueOf(schoolLineMap.get("lowestScore").toString());
            schoolLineYear=schoolLineMap.get("year").toString();
        }
        if (totalScore>schoolLine){
            Integer stuNum = scoreAnalysisService.queryStuNumToLine(schoolLine,totalScore,areaTableName);
            Map<String,Object> resultMap=new HashedMap();
            resultMap.put("schoolId",schoolId);
            resultMap.put("batchName",batchName);
            resultMap.put("schoolName",name);
            resultMap.put("totalScore",totalScore);
            resultMap.put("stuNum",-stuNum);
            resultMap.put("batchLine",scoreUtil.getBatchScore(batch,areaId,majorType));
            resultMap.put("schoolLine",schoolLine);
            resultMap.put("year",schoolLineYear);
            return resultMap;
        }
        Integer stuNum = scoreAnalysisService.queryStuNumToLine(totalScore,schoolLine,areaTableName);

        Map<String,Object> resultMap=new HashedMap();

        resultMap.put("schoolId",schoolId);
        resultMap.put("schoolName",name);
        resultMap.put("batchName",batchName);
        resultMap.put("totalScore",totalScore);
        resultMap.put("stuNum",stuNum);
        resultMap.put("addScore",totalScore-schoolLine);
        resultMap.put("batchLine",scoreUtil.getBatchScore(batch,areaId,majorType));
        resultMap.put("schoolLine",schoolLine);
        resultMap.put("year",schoolLineYear);
        return resultMap;
    }


    /**
     * 查询院校近三年成绩
     * @param universityId
     * @param areaId
     * @param majorType
     * @return
     */
    @RequestMapping(value = "/queryUniversityScore",method = RequestMethod.GET)
    @ResponseBody
    public Object queryUniversityScore(@RequestParam long universityId,
                                       @RequestParam long areaId,
                                       @RequestParam Integer majorType,
                                       @RequestParam Integer batch){
        List<Map<String,Object>> resultMaps = scoreAnalysisService.queryUniversityScore(universityId,areaId,majorType,batch);
        return resultMaps;
    }


    /**
     * 根据薄弱科目给当前用户推荐智学堂学习
     * @return
     */
    @RequestMapping(value = "/querySubjectByGrade",method = RequestMethod.GET)
    @ResponseBody
    public Object querySubjectByGrade(@RequestParam long userId,@RequestParam String subject){
        //高一课程地址
        Map<String,Object> subjectMap1 = new HashedMap();
        subjectMap1.put("语文","http://xuetang.zhigaokao.cn/course/explore/gyyuwen?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("数学","http://xuetang.zhigaokao.cn/course/explore/gyshuxue?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("外语","http://xuetang.zhigaokao.cn/course/explore/gyyingyu?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("物理","http://xuetang.zhigaokao.cn/course/explore/gywuli?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("化学","http://xuetang.zhigaokao.cn/course/explore/gyhuaxue?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("生物","http://xuetang.zhigaokao.cn/course/explore/gyshengwu?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("政治","http://xuetang.zhigaokao.cn/course/explore/gyzhengzhi?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("历史","http://xuetang.zhigaokao.cn/course/explore/gylishi?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap1.put("地理","http://xuetang.zhigaokao.cn/course/explore/gydili?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");

        //高二课程地址
        Map<String,Object> subjectMap2 = new HashedMap();
        subjectMap2.put("语文","http://xuetang.zhigaokao.cn/course/explore/geyuwen?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("数学","http://xuetang.zhigaokao.cn/course/explore/geshuxue?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("外语","http://xuetang.zhigaokao.cn/course/explore/geyingyu?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("物理","http://xuetang.zhigaokao.cn/course/explore/gewuli?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("化学","http://xuetang.zhigaokao.cn/course/explore/gehuaxue?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("生物","http://xuetang.zhigaokao.cn/course/explore/geshengwu?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("政治","http://xuetang.zhigaokao.cn/course/explore/gezhengzhi?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("历史","http://xuetang.zhigaokao.cn/course/explore/gelishi?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");
        subjectMap2.put("地理","http://xuetang.zhigaokao.cn/course/explore/gedili?fliter%5Btype%5D=all&fliter%5Bprice%5D=all&fliter%5BcurrentLevelId%5D=all&orderBy=latest");

        Integer grade = scoreAnalysisService.queryUserGrade(userId);
        if(grade==null || grade==2 || grade == 3){
            //推荐高二课程
            return subjectMap2.get(subject);

        }else {
            //推荐高一课程
            return subjectMap1.get(subject);
        }

    }

}
