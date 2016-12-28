package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.common.Constants;
import cn.thinkjoy.gk.dao.IZGK3in7DAO;
import cn.thinkjoy.gk.dao.selcourse.ISelMajorDao;
import cn.thinkjoy.gk.dao.selcourse.ISelUniversityDao;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.service.selcourse.ISelMajorService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<MajoredDto> getMajorSalary(int pageNo,int pageSize) {
        return iSelMajorDao.getMajorSalary((pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public List<UniversityOrMajorPojo> selectMajorByWords(String queryValue) {
        return iSelMajorDao.selectMajorByWords(queryValue);
    }

    @Override
    public SelMajorPojo selectMajorById(String majorId) {
        SelMajorPojo selMajorPojo=iSelMajorDao.selectMajorById(majorId);
        if(StringUtils.isNotBlank(selMajorPojo.getFmRatio())) {
            String tt = selMajorPojo.getFmRatio();
            String nan = tt.split(" ")[0];
            String nan1 = tt.split(" ")[1];
            String nv = tt.split(" ")[3];
            String nv1 = tt.split(" ")[4];
            List<SexPercentPojo> sexPercentList = new ArrayList<>();
            SexPercentPojo sexPercentPojo = new SexPercentPojo();
            sexPercentPojo.setSexName(nan);
            sexPercentPojo.setPercent(nan1);
            sexPercentList.add(sexPercentPojo);
            SexPercentPojo sexPercentPojo2 = new SexPercentPojo();
            sexPercentPojo2.setSexName(nv);
            sexPercentPojo2.setPercent(nv1);
            sexPercentList.add(sexPercentPojo2);
            selMajorPojo.setSexPercent(sexPercentList);
            selMajorPojo.setFmRatio(null);
        }
        return selMajorPojo;
    }
}
