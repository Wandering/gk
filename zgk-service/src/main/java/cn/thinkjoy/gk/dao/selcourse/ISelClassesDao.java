package cn.thinkjoy.gk.dao.selcourse;

import cn.thinkjoy.gk.pojo.NumberPojo;

import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelClassesDao {
    NumberPojo selectSchoolAndMajorNumberBySubjects(Map<String,Object> map);
}
