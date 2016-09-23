package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.common.Constants;
import cn.thinkjoy.gk.dao.IZGK3in7DAO;
import cn.thinkjoy.gk.dao.selcourse.ISelMajorDao;
import cn.thinkjoy.gk.dao.selcourse.ISelUniversityDao;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.selcourse.ISelMajorService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
@Service
public class SelMajorServiceImpl implements ISelMajorService {

    @Autowired
    private ISelMajorDao iSelMajorDao;

    @Override
    public List<SelSubjectNumberPojo> selectSubjectNumber(Map<String, Object> map) {
        return iSelMajorDao.selectSubjectNumber(map);
    }

    @Override
    public List<UniversityBatchNumberPojo> selectUniversityBatchNumber(Map<String, Object> map) {
        return iSelMajorDao.selectUniversityBatchNumber(map);
    }

    @Override
    public List<MajorPojo> selectMajorList(Map<String, Object> map) {
        return iSelMajorDao.selectMajorList(map);
    }

    @Override
    public Integer selectMajorListCount(Map<String, Object> map) {
        return iSelMajorDao.selectMajorListCount(map);
    }

    @Override
    public List<SelSubjectNumberPojo> getMajorSubStatistics() {

        List<SelSubjectNumberPojo> pojos = Lists.newArrayList();
        for(int i=0;i< Constants.sub_arr.length;i++){
            SelSubjectNumberPojo pojo = iSelMajorDao.getMajorCountBySub(Constants.sub_arr[i]);
            pojo.setSubject(Constants.sub_arr[i]);
            pojos.add(pojo);
        }

        return pojos;
    }

    @Override
    public List<MajoredDto> getMajorSalary() {
        return iSelMajorDao.getMajorSalary(Constants.salary_size);
    }
}
