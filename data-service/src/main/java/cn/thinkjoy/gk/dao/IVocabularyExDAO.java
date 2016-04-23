package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.VocabularyPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/12/29.
 */
public interface IVocabularyExDAO {
    /** 查询高考词条列表 */
    List<VocabularyPojo> getVocabularyPojoList(Map<String,Object> params);

    /** 查询高考词条详情 */
    VocabularyPojo getVocabularyPojoById(long id);

}
