package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.dao.selcourse.ISelClassesDao;
import cn.thinkjoy.gk.dao.selcourse.ISelUniversityDao;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
@Service
public class SelUniversityServiceImpl implements ISelUniversityService {

    @Autowired
    private ISelUniversityDao iSelUniversityDao;

    @Override
    public SelUniversityPojo selectUniversityById(Map<String, Object> map) {
        return iSelUniversityDao.selectUniversityById(map);
    }

    @Override
    public List<EduLevelNumberPojo> selectEduLevelByUniversityId(Map<String, Object> map) {
        return iSelUniversityDao.selectEduLevelByUniversityId(map);
    }

    @Override
    public List<MajorPojo> selectMajorList(Map<String, Object> map) {
        return iSelUniversityDao.selectMajorList(map);
    }

    @Override
    public Integer selectMajorListCount(Map<String, Object> map) {
        return iSelUniversityDao.selectMajorListCount(map);
    }
}
