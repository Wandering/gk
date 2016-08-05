package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IScoreAnalysisDAO;
import cn.thinkjoy.gk.service.IScoreAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/27.
 */
@Service
public class ScoreAnalysisServiceImpl implements IScoreAnalysisService {


    @Autowired
    private IScoreAnalysisDAO scoreAnalysisDAO;

    @Override
    public Map<String, Object> queryScoreRecordByUserId(long userId) {
        return scoreAnalysisDAO.queryScoreRecordByUserId(userId);
    }

    @Override
    public int insertScoreRecord(Map<String, Object> map) {
        return scoreAnalysisDAO.insertScoreRecord(map);
    }

    @Override
    public Map<String, Object> queryInfoByRecordId(long recordId) {
        return scoreAnalysisDAO.queryInfoByRecordId(recordId);
    }

    @Override
    public List<Map<String, Object>> queryAllRecordByUserId(long userId) {
        return scoreAnalysisDAO.queryAllRecordByUserId(userId);
    }

    @Override
    public int queryStuNum(Object totalScore,String areaTableName) {
        return scoreAnalysisDAO.queryStuNum(true,areaTableName);
    }

    @Override
    public int queryAllAreaStuNum(String areaTableName) {
        return scoreAnalysisDAO.queryAllAreaStuNum(areaTableName);
    }

    @Override
    public Integer queryStuNumToLine(Object totalScore,Object scoreLine,String areaTableName) {
        return scoreAnalysisDAO.queryStuNumToLine(totalScore,scoreLine,areaTableName);
    }

    @Override
    public String querySchoolNameById(Object id) {
        return scoreAnalysisDAO.querySchoolNameById(id);
    }

    @Override
    public String queryBatchNameById(Object id) {
        return scoreAnalysisDAO.queryBatchNameById(id);
    }

    @Override
    public int queryProviceRank(Object totalScore,String areaTableName) {
        return scoreAnalysisDAO.queryProviceRank(totalScore,areaTableName);
    }

    @Override
    public String queryScoreLine(long areaId,int majorType,String year) {
        return scoreAnalysisDAO.queryScoreLine(areaId,majorType,year);
    }

    @Override
    public String queryAreaKey(long areaId) {
        return scoreAnalysisDAO.queryAreaKey(areaId);
    }

    @Override
    public List<Map<String, Object>> queryUnivsersityBatch(long areaId,long schooleId,String year) {
        return scoreAnalysisDAO.queryUnivsersityBatch(areaId,schooleId,year);
    }

    @Override
    public Map<String, Object> queryLastTarget(long userId) {
        return scoreAnalysisDAO.queryLastTarget(userId);
    }

    @Override
    public int insertTarget(Map<String, Object> map) {
        return scoreAnalysisDAO.insertTarget(map);
    }

    @Override
    public Float queryUnivsersityLowestScore(long schoolId,long areaId,int batch,int majorType, String year) {
        return scoreAnalysisDAO.queryUnivsersityLowestScore(schoolId,areaId,batch,majorType,year);
    }

    @Override
    public int countUniversity(long areaId,int batch, int majorType, String year, Float difference,Float line,int bc) {
        return scoreAnalysisDAO.countUniversity(areaId,batch,majorType,year,difference,line,bc);
    }

    @Override
    public List<Map<String, Object>> queryUniversityByScore(long areaId, int batch, int majorType, String year,Float difference,Float line,  Float totalScore,int bc) {
        return scoreAnalysisDAO.queryUniversityByScore(areaId,batch,majorType,year,difference,line,totalScore,bc);
    }

    @Override
    public List<Map<String, Object>> queryHighSchoolByCountyId(long countyId, String schoolName) {
        return scoreAnalysisDAO.queryHighSchoolByCountyId(countyId,schoolName);
    }

    @Override
    public int setUserInfo(Map<String, Object> map) {
        return scoreAnalysisDAO.setUserInfo(map);
    }

    @Override
    public Map<String, Object> queryUserInfo(long userId) {
        return scoreAnalysisDAO.queryUserInfo(userId);
    }

    @Override
    public List<Map<String, Object>> queryUniversityScore(long universityId,String year, long areaId,  Integer majorType) {
        return scoreAnalysisDAO.queryUniversityScore(universityId,year,areaId,majorType);
    }

    @Override
    public boolean isExistScore(Object totalScore,String areaTableName) {
        return scoreAnalysisDAO.isExistScore(totalScore,areaTableName);
    }

    @Override
    public List<Map<String, Object>> queryLowstUniversity(long areaId,int majorType,Float totalScore,String year) {
        return scoreAnalysisDAO.queryLowstUniversity(areaId,majorType,totalScore,year);
    }

    @Override
    public Integer queryUserGrade(long userId) {
        return scoreAnalysisDAO.queryUserGrade(userId);
    }
}
