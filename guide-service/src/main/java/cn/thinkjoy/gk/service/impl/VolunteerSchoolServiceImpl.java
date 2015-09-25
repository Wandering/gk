/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: jx-market
 * $Id:  VolunteerSchoolServiceImpl.java 2015-09-22 19:07:37 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.gk.dao.IVolunteerSchoolDAO;
import cn.thinkjoy.gk.dao.IVolunteerSchoolExDAO;
import cn.thinkjoy.gk.domain.VolunteerSchool;
import cn.thinkjoy.gk.service.IVolunteerSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("VolunteerSchoolServiceImpl")
public class VolunteerSchoolServiceImpl extends AbstractPageService<IBaseDAO<VolunteerSchool>, VolunteerSchool> implements IVolunteerSchoolService<IBaseDAO<VolunteerSchool>,VolunteerSchool> {
    @Autowired
    private IVolunteerSchoolDAO volunteerSchoolDAO;
    @Autowired
    private IVolunteerSchoolExDAO volunteerSchoolExDAO;

    @Override
    public IBaseDAO<VolunteerSchool> getDao() {
        return volunteerSchoolDAO;
    }


    @Override
    public BizData4Page queryPageByDataPerm(String resUri, Map conditions, int curPage, int offset, int rows) {
        List mainData = volunteerSchoolExDAO.queryPage(conditions, offset, rows, "sort", "asc");
        int records = this.getDao().count(conditions);
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(curPage);
        bizData4Page.setRecords(records);
        int total = records / rows;
        int mod = records % rows;
        if(mod > 0) {
            ++total;
        }

        bizData4Page.setTotal(total);

        return bizData4Page;
    }

    @Override
    public void addHits(long id, int hits) {
        volunteerSchoolExDAO.addHits(id, hits);
    }


//    @Override
//    public void insert(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void update(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void updateNull(BaseDomain entity) {
//
//    }
//
//    @Override
//    public void deleteById(Long id) {
//
//    }
//
//    @Override
//    public void deleteByProperty(String property, Object value) {
//
//    }
//
//    @Override
//    public BaseDomain fetch(Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain findOne(@Param("property") String property, @Param("value") Object value) {
//        return null;
//    }
//
//    @Override
//    public List findList(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public void deleteByCondition(Map condition) {
//
//    }
//
//    @Override
//    public void updateMap(@Param("map") Map entityMap) {
//
//    }
//
//    @Override
//    public List<VolunteerSchool> findAll() {
//        return volunteerSchoolDAO.findAll();
//    }
//
//    @Override
//    public List like(String property, Object value) {
//        return null;
//    }
//
//    @Override
//    public Long selectMaxId() {
//        return null;
//    }
//
//    @Override
//    public BaseDomain updateOrSave(BaseDomain baseDomain, Long id) {
//        return null;
//    }
//
//    @Override
//    public BaseDomain selectOne(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public List selectList(String mapperId, Object obj) {
//        return null;
//    }
//
//    @Override
//    public Class getEntityClass() {
//        return null;
//    }
//
//    @Override
//    public int count(Map condition) {
//        return 0;
//    }
//
//    @Override
//    public BaseDomain queryOne(Map condition) {
//        return null;
//    }
//
//    @Override
//    public List queryList(@Param("condition") Map condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy) {
//        return null;
//    }
//
//    @Override
//    public List queryPage(@Param("condition") Map condition, @Param("offset") int offset, @Param("rows") int rows) {
//        return null;
//    }
//
//    @Override
//    protected VolunteerSchoolDAO getDao() {
//        return volunteerSchoolDAO;
//    }
//



}
