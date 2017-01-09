package cn.thinkjoy.gk.service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 17/1/9.
 */
public interface IOperateDataService {

    /**
     * 查询阿里相关数据
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,String>> getAliData(String startTime,String endTime);
}
