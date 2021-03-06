package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.ScoreAnalysisNumberPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/27.
 */
public interface IScoreAnalysisDAO {


    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     * @param userId
     * @return
     */
    Map<String,Object> queryScoreRecordByUserId(@Param("userId") long userId);

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
    List<Map<String,Object>> queryAllRecordByUserId(@Param("userId")long userId);

    /**
     * 查询一分之后会超越多少人
     * @param totalScore
     * @param areaTableName
     * @return
     */
    Integer queryStuNum(@Param("totalScore")Object totalScore,
                    @Param("areaTableName")String areaTableName);

    /**
     * 查询分数线之上有多少人
     * @param areaTableName
     * @return
     */
    Integer queryAllAreaStuNum(@Param("areaTableName")String areaTableName);

    /**
     * 查询分数到分数线之间有多少人
     * @param totalScore
     * @param scoreLine
     * @param areaTableName
     * @return
     */
    Integer queryStuNumToLine(@Param("totalScore")Object totalScore,
                          @Param("scoreLine")Object scoreLine,
                          @Param("areaTableName")String areaTableName);

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
    Integer queryProviceRank(@Param("totalScore")Object totalScore,
                         @Param("areaTableName")String areaTableName);

    /**
     * 查询分数排名
     * @param totalScore
     * @param areaTableName
     * @return
     */
    Integer queryProviceRank2(@Param("totalScore")Object totalScore,
                             @Param("areaTableName")String areaTableName);
    /**
     * 查询分数线串
     * @param areaId
     * @param majorType
     * @param year
     * @return
     */
    String queryScoreLine(@Param("areaId")long areaId,
                          @Param("majorType")int majorType,
                          @Param("year")String year);

    /**
     * 根据区域代码查询区域简写
     * @param areaId
     * @return
     */
    String queryAreaKey(@Param("areaId")long areaId);

    /**
     * 查询院校批次
     * @param areaId
     * @param schooleId
     * @param year
     * @return
     */
    List<Map<String,Object>> queryUnivsersityBatch(@Param("areaId")long areaId,
                                                   @Param("schooleId")long schooleId,
                                                   @Param("year")String year,
                                                   @Param("majorType")Integer majorType);

    /**
     *  获取最后一次目标院校
     * @param userId
     * @return
     */
    Map<String,Object> queryLastTarget(@Param("userId")long userId);

    /**
     * 添加目标院校
     * @param map
     * @return
     */
    int insertTarget(Map<String,Object> map);

    /**
     * 查询院校最低分
     * @param schoolId
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    Map<String,Object> queryUnivsersityLowestScore(@Param("schoolId")long schoolId,
                                      @Param("areaId")long areaId,
                                      @Param("batch")Integer batch,
                                      @Param("majorType")Integer majorType,
                                      @Param("year")String year);


    /**
     * 查询院校专业平均分
     * @param schoolId
     * @param areaId
     * @param majorCode
     * @param year
     * @return
     */
    List<Map<String,Object>> queryMajorLowestScore(@Param("schoolId")long schoolId,
                                                   @Param("areaId")long areaId,
                                                   @Param("majorCode")String majorCode,
                                                   @Param("year")String year);


    /**
     * 统计当前推荐学校数量
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    int countUniversity(@Param("areaId")long areaId,
                        @Param("batch")int batch,
                        @Param("majorType")int majorType,
                        @Param("year")String year,
                        @Param("difference")Float difference,
                        @Param("line")Float line,
                        @Param("bc")int bc
    );
    /**
     * 获取推荐学校详情
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    List<Map<String,Object>>  queryUniversityByScore(@Param("areaId")long areaId,
                                                     @Param("batch")int batch,
                                                     @Param("majorType")int majorType,
                                                     @Param("year")String year,
                                                     @Param("difference")Float difference,
                                                     @Param("line")Float line,
                                                     @Param("totalScore")Float totalScore,
                                                     @Param("bc")int bc,
                                                     @Param("userId")long userId
                                                     );

    /**
     * 统计当前推荐学校数量
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    int countJSUniversity(@Param("areaId")long areaId,
                        @Param("batch")int batch,
                        @Param("majorType")int majorType,
                        @Param("year")String year,
                        @Param("difference")Float difference,
                        @Param("line")Float line,
                        @Param("bc")int bc,
                        @Param("xcRanks")List<String> xcRanks
    );
    /**
     * 获取推荐学校详情
     * @param areaId
     * @param batch
     * @param majorType
     * @param year
     * @return
     */
    List<Map<String,Object>>  queryJSUniversityByScore(@Param("areaId")long areaId,
                                                     @Param("batch")int batch,
                                                     @Param("majorType")int majorType,
                                                     @Param("year")String year,
                                                     @Param("difference")Float difference,
                                                     @Param("line")Float line,
                                                     @Param("totalScore")Float totalScore,
                                                     @Param("bc")int bc,
                                                     @Param("xcRanks")List<String> xcRanks,
                                                     @Param("userId")long userId);
    /**
     * 统计当前推荐学校数量
     * @param map
     * @return
     */
    int countZJUniversity(Map<String,Object> map
    );

    /**
     * 获取推荐学校详情
     * @param map
     * @return
     */
    List<Map<String,Object>>  queryZJUniversityByScore(Map<String,Object> map);

    /**
     * 根据区Id获取当前区高中
     * @param countyId
     * @return
     */
    List<Map<String,Object>>  queryHighSchoolByCountyId(@Param("countyId")long countyId,
                                                        @Param("schoolName")String schoolName);

    /**
     * 设置用户信息
     * @param map
     * @return
     */
    int  setUserInfo(Map<String,Object> map);


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
    List<Map<String,Object>>  queryUniversityScore(@Param("universityId")long universityId,@Param("areaId")long areaId,@Param("majorType")Integer majorType,@Param("batch")Integer batch);

    /**
     * 判断分数是否在一分一段表中存在
     * @param totalScore
     * @param areaTableName
     * @return
     */
    boolean isExistScore(@Param("totalScore")Object totalScore,
                         @Param("areaTableName")String areaTableName);
    /**
     * 判断分数是否在一分一段表中存在(最大值)
     * @param totalScore
     * @param areaTableName
     * @return
     */
    boolean isExistMaxScore(@Param("totalScore")Object totalScore,
                         @Param("areaTableName")String areaTableName);

    /**
     * 取得前十个高职院校
     * @param areaId
     * @param totalScore
     * @param majorType
     * @param year
     * @return
     */
    List<Map<String,Object>> queryLowstUniversity(@Param("areaId")long areaId,
                                 @Param("majorType")int majorType,
                                 @Param("totalScore")Float totalScore,
                                 @Param("year")String year,
                                 @Param("userId")long userId);

    /**
     * 获取用户年级
     * @param userId
     * @return
     */
    Integer queryUserGrade(@Param("userId")long userId);
    /**
     * 根据areaId获取当前省份总分 可能为空
     * @param areaId
     * @return
     */
    Integer queryTotalScoreByAreaId(@Param("areaId")long areaId);

    /**
     * 获取用户上一次成绩
     * @param userId
     * @param lastId
     * @return
     */
    Float queryLastScore(@Param("userId")long userId,@Param("lastId")long lastId);

    /**
     * 获取当前省份当年参加高考人数 当不存在的时候为0或null
     * @param areaId
     * @return
     */
    Integer queryPeoNumByAreaAndType(@Param("areaId")long areaId,@Param("majorType")int majorType);

    /**
     *获取当前用户标签
     * @param type
     * @param configs
     * @return
     */
    List<String> queryLabelByTypeAndConfig(@Param("type")Integer type,@Param("configs")List<Integer> configs);

    /**
     * 查询用户历史记录
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryHistoryScore(@Param("userId")long userId,@Param("rows")Integer rows,@Param("areaId")Long areaId);

    /**
     * 根据院校ID和地区code查询专业
     * @param map
     * @return
     */
    List<Map<String,Object>> queryMajorBySchoolIdAndAreaId(Map<String,Object> map);

    /**
     * 获取院校测评等级
     * @param map
     * @return
     */
    String queryUniversitySubLevel(Map<String,Object> map);


    /**
     *  获取用户测评条数
     * @param userId
     * @return
     */
    Integer queryScoreCount(long userId);

    /**
     * 获取用户年级
     * @param userId
     * @return
     */
    String queryUserGradeInfo(long userId);

    /**
     *
     */
    Integer countMajorRepeat(Map<String,Object> map);

    ScoreAnalysisNumberPojo queryHistoryScoreAnalysis(Map<String,Object> map);
}
