package cn.thinkjoy.gk.service.selcourse;

import cn.thinkjoy.gk.pojo.MajorBatchNumberPojo;
import cn.thinkjoy.gk.pojo.MajorPojo;
import cn.thinkjoy.gk.pojo.NumberPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelClassesService {
    NumberPojo selectSchoolAndMajorNumberBySubjects(Map<String,Object> map);

    List<MajorBatchNumberPojo> selectMajorNumberByBatch(Map<String,Object> map);

    List<MajorPojo> selectMajorList(Map<String,Object> map);

    int selectMajorListCount(Map<String,Object> map);
}
