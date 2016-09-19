package cn.thinkjoy.gk.service.selcourse;

import cn.thinkjoy.gk.pojo.NumberPojo;

import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelClassesService {
    NumberPojo selectSchoolAndMajorNumberBySubjects(Map<String,Object> map);
}
