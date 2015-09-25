/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: jx-market
 * $Id:  VolunteerSchoolDAO.java 2015-09-22 19:07:37 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.VolunteerSchool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IVolunteerSchoolExDAO {

    /** 不带富文本正文的文章列表 */
    List<VolunteerSchool> queryPage(@Param("condition") Map<String, Object> condition,
                                    @Param("offset") int offset, @Param("rows") int rows,
                                    @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);
    /** 增加点击量 */
    void addHits(@Param("id") long id, @Param("hits") int hits);
}
