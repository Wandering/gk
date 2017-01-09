package cn.thinkjoy.gk.api;

import java.util.Map;

/**
 * Created by yangyongping on 2016/11/15.
 */
public interface IApeskApi {
    /**
     * 获取才储接口
     * @param acId
     * @return
     */
    public Map<String, Object> queryApeskUrl(Long userId, Long areaId, Integer acId, String key);

    /**
     * 获取报告地址
     * @param liangbiao
     * @param acId
     * @return
     */
    Map<String, Object> queryReportUrl(String liangbiao, Integer acId, Long userId);

    /**
     * 获取报告地址
     * @return
     */
    Map<String, Object> queryReportUrl(Integer id);

    /**
     * 获取测评列表
     * @param areaId
     * @return
     */
    Map<String,Object> getApeskResult(Long userId,Long areaId);
}
