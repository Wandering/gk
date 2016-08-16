package cn.thinkjoy.gk.controller.score;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import cn.thinkjoy.gk.common.ScoreUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

//        特殊省份处理
//        获取用户信息
//        判断用户所在省份
//        是特殊省份
//        if(){
//
//        }else {
//
//        }
//        不是特殊省份
        return scoreAnalysisService.queryScoreRecordByUserId(userId);
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

        return scoreAnalysisService.insertScoreRecord(userId,areaId,majorType,scores);
    }


    /**
     * 用户根据记录ID查询分数的信息和排名信息
     * @return
     */
    @RequestMapping(value = "/queryInfoByRecordId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryInfoByRecordId(@RequestParam long recordId){
        return scoreAnalysisService.queryInfoByRecordId(recordId);
    }

    /**
     * 根据用户Id和用户来源查询用户所有的提交记录
     * @return
     */
    @RequestMapping(value = "/queryAllRecordByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Object queryAllRecordByUserId(@RequestParam long userId){
        return scoreAnalysisService.queryAllRecordByUserId(userId);
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
        return scoreAnalysisService.recommendSchool(totalScore,areaId,majorType);
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
        return scoreAnalysisService.queryGapBySchoolIdAndBatch(recordId,schoolId,batch,userId);
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
                                       Integer batch){
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
