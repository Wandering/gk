package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.dao.selcourse.ISelClassesDao;
import cn.thinkjoy.gk.pojo.*;
import cn.thinkjoy.gk.pojo.MajorTop3Pojo;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public Bases[] selectMajorTop3() {
        List<MajorTop3Pojo> dataList=iSelClassesDao.selectMajorTop3();
        Bases[] basesList = getBaseses();
        for (MajorTop3Pojo data:dataList){
            for(Bases base:basesList){
                String[] ss1=data.getSelectSubject().split(" ");
                if(ss1.length==0){
                    base.setSchoolNumber(base.getSchoolNumber()+1);
                    base.getSchoolList().add(data.getSchoolCode());
                }
                else {
                    for (String ss : ss1) {
                        if (base.getName().contains(ss)) {
                            base.setMajorNumber(base.getMajorNumber() + 1);
                            boolean flag = true;
                            for (String ss2 : base.getSchoolList()) {
                                if (ss2.equals(data.getSchoolCode())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                base.setSchoolNumber(base.getSchoolNumber() + 1);
                                base.getSchoolList().add(data.getSchoolCode());
                            }
                            break;
                        }
                    }
                }
            }

        }
        Arrays.sort(basesList);
        Bases[] returnBases=new Bases[3];
        for(int i=0;i<3;i++){
            returnBases[i]=basesList[i];
            returnBases[i].setSchoolList(null);
        }
        return returnBases;
    }


    private Bases[] getBaseses() {

        Bases base1=new Bases("物理 化学 历史",0,0);
        Bases base2=new Bases("物理 生物 历史",0,0);
        Bases base3=new Bases("物理 化学 地理",0,0);
        Bases base4=new Bases("物理 化学 思想政治",0,0);
        Bases base5=new Bases("物理 历史 通用技术",0,0);
        Bases base6=new Bases("物理 生物 地理",0,0);
        Bases base7=new Bases("物理 生物 思想政治",0,0);
        Bases base8=new Bases("物理 历史 思想政治",0,0);
        Bases base9=new Bases("物理 历史 地理",0,0);
        Bases base10=new Bases("物理 地理 通用技术",0,0);
        Bases base11=new Bases("物理 思想政治 通用技术",0,0);
        Bases base12=new Bases("物理 地理 思想政治",0,0);
        Bases base13=new Bases("物理 化学 通用技术",0,0);
        Bases base14=new Bases("物理 生物 通用技术",0,0);
        Bases base15=new Bases("化学 历史 通用技术",0,0);
        Bases base16=new Bases("物理 化学 生物",0,0);
        Bases base17=new Bases("化学 地理 通用技术",0,0);
        Bases base18=new Bases("化学 思想政治 通用技术",0,0);
        Bases base19=new Bases("化学 历史 地理",0,0);
        Bases base20=new Bases("化学 生物 历史",0,0);
        Bases base21=new Bases("化学 历史 思想政治",0,0);
        Bases base22=new Bases("化学 地理 思想政治",0,0);
        Bases base23=new Bases("化学 生物 通用技术",0,0);
        Bases base24=new Bases("化学 生物 地理",0,0);
        Bases base25=new Bases("化学 生物 思想政治",0,0);
        Bases base26=new Bases("生物 历史 通用技术",0,0);
        Bases base27=new Bases("生物 地理 通用技术",0,0);
        Bases base28=new Bases("生物 思想政治 通用技术",0,0);
        Bases base29=new Bases("生物 历史 地理",0,0);
        Bases base30=new Bases("生物 历史 思想政治",0,0);
        Bases base31=new Bases("生物 地理 思想政治",0,0);
        Bases base32=new Bases("历史 地理 通用技术",0,0);
        Bases base33=new Bases("历史 思想政治 通用技术",0,0);
        Bases base34=new Bases("地理 思想政治 通用技术",0,0);
        Bases base35=new Bases("历史 地理 思想政治",0,0);
        Bases[] basesList = {base1,base2,base3,base4,base5,base6,base7,base8,base9,base10,base11,base12,base13,base14,base15,base16,base17,
                base18, base19, base20, base21, base22, base23, base24, base25, base26, base27, base28, base29, base30, base31, base32, base33, base34, base35};
        return basesList;
    }

}

