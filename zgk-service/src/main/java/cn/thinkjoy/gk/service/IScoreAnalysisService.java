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
     * @param userId
     * @param areaId
     * @param majorType
     * @param scores
     * @return
     */
    Map<String, Object> insertScoreRecord(long userId,
                                          long areaId,
                                          Integer majorType,
                                          Map<String,Object> scores);

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
    Integer queryProviceRank(Object totalScore,
                         String areaTableName);

    /**
     * 查询分数排名
     * @param totalScore
     * @param areaTableName
     * @return
     */
    Integer queryProviceRank2(Object totalScore,
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
                                                   String year,
                                                   Integer majorType);

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
    Map<String,Object> queryUnivsersityLowestScore(long schoolId,
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
                                                     int bc,
                                                     long userId);

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
     * @param areaId
     * @param majorType
     * @return
     */
    List<Map<String,Object>>  queryUniversityScore(long universityId,
                                                   long areaId,
                                                   Integer majorType,
                                                   Integer batch);

    /**
     * 判断分数是否在一分一段表中存在
     * @param totalScore
     * @param areaTableName
     * @return
     */
    boolean isExistScore(Object totalScore,
                         String areaTableName);

    /**
     * 判断分数是否在一分一段表中存在(最大值)
     * @param totalScore
     * @param areaTableName
     * @return
     */
    boolean isExistMaxScore(Object totalScore,
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

    /**
     * 根据areaId获取总分
     * @param areaId
     * @return
     */
    Integer queryTotalScoreByAreaId(long areaId);

    /**
     * 获取用户上一次成绩
     * @param userId
     * @param lastId
     * @return
     */
    Float queryLastScore(long userId,long lastId);

    /**
     * 获取当前省份当年参加高考人数 当不存在的时候为0或null
     * @param areaId
     * @return
     */
    Integer queryPeoNumByAreaAndType(long areaId,int majorType);

    /**
     *获取当前用户标签
     * @param type
     * @param configs
     * @return
     */
    List<String> queryLabelByTypeAndConfig(Integer type,List<Integer> configs);


    Object recommendSchool(float totalScore,long areaId,int majorType,long userId);

    Object queryGapBySchoolIdAndBatch(long recordId,
                                      Long schoolId,
                                      Integer batch,
                                      long userId);

    Object queryGapBySchoolIdAndMajor(long recordId,
                                      Long schoolId,
                                      String majorCode,
                                      long userId);

    List<String> queryHistoryScore(long userId,Integer rows);

    /**
     * 江苏算法
     * @param totalScore
     * @param areaId
     * @param majorType
     * @return
     */
    public Object recommendSchoolJS(float totalScore, long areaId, int majorType,long userId);

    /**
     * 浙江算法
     * @param totalScore
     * @param areaId
     * @return
     */
    public Object recommendSchoolZJ(float totalScore, long areaId,long userId);

    /**
     * 根据院校ID和地区code查询专业
     * @param areaId
     * @param universityId
     * @return
     */
    List<Map<String,Object>> queryMajorBySchoolIdAndAreaId(long areaId,long universityId);
}
