package cn.thinkjoy.gk.service.selcourse;

import cn.thinkjoy.gk.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelUniversityService {

    SelUniversityPojo selectUniversityById(Map<String, Object> map);

    List<EduLevelNumberPojo> selectEduLevelByUniversityId(Map<String, Object> map);

    List<MajorPojo> selectMajorList(Map<String,Object> map);

    Integer selectMajorListCount(Map<String, Object> map);
}
