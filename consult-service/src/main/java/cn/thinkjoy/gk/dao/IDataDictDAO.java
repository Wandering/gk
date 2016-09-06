package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.UniversityDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
public interface IDataDictDAO {
    List<Map<String, Object>> queryDictList(@Param("condition")Map<String, Object> map);
    Map<String, Object> queryDictByDictId(@Param("condition")Map<String, Object> map);
}
