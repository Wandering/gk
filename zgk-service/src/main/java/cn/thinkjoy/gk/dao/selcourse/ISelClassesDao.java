package cn.thinkjoy.gk.dao.selcourse;

import cn.thinkjoy.gk.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelClassesDao {
    NumberPojo selectSchoolAndMajorNumberBySubjects(Map<String,Object> map);

    List<MajorBatchNumberPojo> selectMajorNumberByBatch(Map<String,Object> map);

    List<MajorPojo> selectMajorList(Map<String,Object> map);

    int selectMajorListCount(Map<String,Object> map);

    List<UniversityOrMajorPojo> selectUniversityOrMajorByWords(String queryValue);

    List<MajorTop3Pojo> selectMajorTop3(@Param("year") String year);

    /**
     * 查询有招生计划的年份集合
     *
     * @return
     */
    List<String> getEnrollingYear();
}
