package cn.thinkjoy.gk.service;

import java.util.Map;

/**
 * Created by yangguorong on 16/5/5.
 */
public interface IProfessionService {

    /**
     * 根据关键词查询职业基本信息
     *
     * @param keywords
     * @return
     */
    Map<String,String> getProfessionalInfoByKeywords(String keywords);
}
