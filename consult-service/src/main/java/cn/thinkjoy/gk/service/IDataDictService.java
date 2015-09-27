package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.UniversityDict;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
public interface IDataDictService {

    List<Map<String, Object>> queryDictList(Map<String, Object> map);
}
