package cn.thinkjoy.gk.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/27.
 */
public interface IScoreAnalysisService {


    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     * @param userId
     * @return
     */
    Map<String,Object> queryScoreRecordByUserId(long userId);

    /**
     * 添加用户定位分数
     * @param map
     * @return
     */
    int insertScoreRecord(Map<String, Object> map);

    /**
     * 根据ID查询定位分数
     * @param recordId
     * @return
     */
    Map<String,Object> queryInfoByRecordId(long recordId);

    /**
     * 查询用户所有定位分数
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryAllRecordByUserId(long userId);

    /**
     * 查询一分之后会超越多少人
     * @param totalScore
     * @param areaTableName
     * @return
     */
    Integer queryStuNum(Object totalScore,
                    String areaTableName);

    /**
     * 查询分数线之上有多少人
     * @param areaTableName
     * @return
     */
    Integer queryAllAreaStuNum(String areaTableName);

    /**
     * 查询分数到分数线之间有多少人
     * @param totalScore
     * @param scoreLine
     * @param areaTableName
     * @return
     */
    Integer queryStuNumToLine(Object totalScore,
                              Object scoreLine,
                              String areaTableName);

    /**
     * 获取院校name
     * @param id
     * @return
     */
    String querySchoolNameById(Object id);

    /**
     * 获取批次名称
     * @param id
     * @return
     */
    String queryBatchNameById(Object id);

    /**
     * 查询分数排名
     * @param totalScore
     * @param areaTableName
     * @return
     */
    int queryProviceRank(Object totalScore,
                         String areaTableName);

    /**
     * 查询分数线串
     * @param areaId
     * @param majorType
     * @param year
     * @return
     */
    String queryScoreLine(long areaId,
                          int majorType,
                          String year);

    /**
     * 根据区域代码查询区域简写
     * @param areaId
     * @return
     */
    String queryAreaKey(long areaId);

    /**
     * 查询院校批次
     * @param areaId
     * @param schooleId
     * @param year
     * @return
     */
    List<Map<String,Object>> queryUnivsersityBatch(long areaId,
                                                   long schooleId,
                                                   String year);

    /**
     *  获取最后一次目标院校
     * @param userId
     * @return
     */
    Map<String,Object> queryLastTarget(long userId);

    /**
     * 添加目标院校
     * @param map
     * @return
     */
    int insertTarget(Map<String, Object> map);

    /**
     * 查询院校最低分
     * @param schoolId
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    Float queryUnivsersityLowestScore(long schoolId,
                                      long areaId,
                                      int batch,
                                      int majorType,
                                      String year);


    /**
     * 统计当前推荐学校数量
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    int countUniversity(long areaId,
                        int batch,
                        int majorType,
                        String year,
                        Float difference,
                        Float line,
                        int bc
    );

    /**
     * 获取推荐学校详情
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    List<Map<String,Object>>  queryUniversityByScore(long areaId,
                                                     int batch,
                                                     int majorType,
                                                     String year,
                                                     Float difference,
                                                     Float line,
                                                     Float totalScore,
                                                     int bc);

    /**
     * 根据区Id获取当前区高中
     * @param countyId
     * @return
     */
    List<Map<String,Object>>  queryHighSchoolByCountyId(long countyId,
                                                        String schoolName);

    /**
     * 设置用户信息
     * @param map
     * @return
     */
    int  setUserInfo(Map<String, Object> map);


    /**
     *
     * 查询用户信息
     * @param userId
     * @return
     */
    Map<String,Object>  queryUserInfo(long userId);

    /**
     * 查询院校近三年的成绩
     * @param universityId
     * @param year
     * @param areaId
     * @param majorType
     * @return
     */
    List<Map<String,Object>>  queryUniversityScore(long universityId,
                                                   String year,
                                                   long areaId,
                                                   Integer majorType);

    /**
     * 判断分数是否在一分一段表中存在
     * @param totalScore
     * @param areaTableName
     * @return
     */
    boolean isExistScore(Object totalScore,
                         String areaTableName);

    /**
     * 取得前十个高职院校
     * @param areaId
     * @param totalScore
     * @param majorType
     * @param year
     * @return
     */
    List<Map<String,Object>> queryLowstUniversity(long areaId,
                                                  int majorType,
                                                  Float totalScore,
                                                  String year);

    /**
     * 获取用户年级
     * @param userId
     * @return
     */
    Integer queryUserGrade(long userId);
}
