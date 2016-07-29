package cn.thinkjoy.gk.dao;

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
    Map<String, Object> insertScoreRecord(Map<String, Object> map);

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
    int queryStuNum(@Param("totalScore")Object totalScore,
                    @Param("areaTableName")String areaTableName);

    /**
     * 查询分数线之上有多少人
     * @param areaTableName
     * @return
     */
    int queryAllAreaStuNum(@Param("areaTableName")String areaTableName);

    /**
     * 查询分数到分数线之间有多少人
     * @param totalScore
     * @param scoreLine
     * @param areaTableName
     * @return
     */
    int queryStuNumToLine(@Param("totalScore")Object totalScore,
                          @Param("scoreLine")Object scoreLine,
                          @Param("areaTableName")String areaTableName);

    /**
     * 查询分数排名
     * @param totalScore
     * @param areaTableName
     * @return
     */
    int queryProviceRank(@Param("totalScore")Object totalScore,
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
                                                   @Param("year")String year);

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
    Float queryUnivsersityLowestScore(@Param("schoolId")long schoolId,
                                      @Param("areaId")long areaId,
                                      @Param("batch")int batch,
                                      @Param("majorType")int majorType,
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
                                                     @Param("bc")int bc);

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


    Map<String,Object>  queryUserInfo(long userId);


}
