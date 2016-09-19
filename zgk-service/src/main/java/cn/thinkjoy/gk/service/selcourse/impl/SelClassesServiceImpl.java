package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.dao.selcourse.ISelClassesDao;
import cn.thinkjoy.gk.pojo.NumberPojo;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
