package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.dao.selcourse.ISelClassesDao;
import cn.thinkjoy.gk.pojo.MajorBatchNumberPojo;
import cn.thinkjoy.gk.pojo.MajorPojo;
import cn.thinkjoy.gk.pojo.NumberPojo;
import cn.thinkjoy.gk.pojo.UniversityOrMajorPojo;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
@Service
public class SelClassesServiceImpl implements ISelClassesService {

    @Autowired
    private ISelClassesDao iSelClassesDao;

    @Override
    public NumberPojo selectSchoolAndMajorNumberBySubjects(Map<String, Object> map) {
        return iSelClassesDao.selectSchoolAndMajorNumberBySubjects(map);
    }

    @Override
    public List<MajorBatchNumberPojo> selectMajorNumberByBatch(Map<String, Object> map) {
        return iSelClassesDao.selectMajorNumberByBatch(map);
    }

    @Override
    public List<MajorPojo> selectMajorList(Map<String, Object> map) {
        return iSelClassesDao.selectMajorList(map);
    }

    @Override
    public int selectMajorListCount(Map<String, Object> map) {
        return iSelClassesDao.selectMajorListCount(map);
    }

    @Override
    public List<UniversityOrMajorPojo> selectUniversityOrMajorByWords(String queryValue) {
        return iSelClassesDao.selectUniversityOrMajorByWords(queryValue);
    }
}
