package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.VocabularyPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/12/29.
 */
public interface IVocabularyExService {
    /** 查询高考词条列表 */
    List<VocabularyPojo> getVocabularyPojoList(Map<String,Object> params);

    /** 查询高考词条详情 */
    VocabularyPojo getVocabularyPojoById(long id);
}
