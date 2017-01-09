package cn.thinkjoy.gk.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 17/1/9.
 */
public interface IOperateDataDAO {

    /**
     * 获取点击图标总用户数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> getClickIconCount(
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 获取完善信息总用户数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> getPerfectInfoCount(
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 获取测评总用户数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> getEvaluationCount(
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 获取录取预测总用户数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> getEnrollingPredictCount(
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}
