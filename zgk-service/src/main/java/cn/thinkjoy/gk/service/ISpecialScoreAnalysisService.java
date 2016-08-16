package cn.thinkjoy.gk.service;

import java.util.Map;

/**
 * Created by yangyongping on 16/8/16.
 *
 * 处理特殊省份浙江和江苏的接口
 *
 */
public interface ISpecialScoreAnalysisService {

    /**
     * 根据用户Id和用户来源查询用户最新的提交记录
     * @param userId
     * @return
     */
    Object queryScoreRecordByUserId(long userId);


    Map<String, Object> insertScoreRecord(long userId,
                                                 long areaId,
                                                 Integer majorType,
                                                 Map<String, Object> scores);


    
}
