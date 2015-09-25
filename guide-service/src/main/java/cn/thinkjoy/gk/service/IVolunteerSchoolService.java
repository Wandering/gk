/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: jx-market
 * $Id:  VolunteerSchoolService.java 2015-09-22 19:07:37 $
 */

package cn.thinkjoy.gk.service;
import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;
import cn.thinkjoy.gk.domain.VolunteerSchool;

import java.util.List;
import java.util.Map;

public interface IVolunteerSchoolService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T>{

    /** 增加点击量 */
    void addHits(long id, int hits);
    /** 可以排序的分页 */
    public BizData4Page<VolunteerSchool> queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows, String orderBy, String sortBy);
}
