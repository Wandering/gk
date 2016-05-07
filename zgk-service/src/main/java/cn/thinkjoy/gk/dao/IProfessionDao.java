package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.entity.Profession;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangguorong on 16/5/5.
 */
public interface IProfessionDao {

    /**
     * 根据关键词查询职业基本信息
     *
     * @param keywords
     * @return
     */
    List<Profession> getProfessionalInfoByKeywords(@Param("keywords") String keywords);
}
