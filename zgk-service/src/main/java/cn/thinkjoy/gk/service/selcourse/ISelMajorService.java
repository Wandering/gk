package cn.thinkjoy.gk.service.selcourse;

import cn.thinkjoy.gk.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelMajorService {

    List<SelSubjectNumberPojo> selectSubjectNumber(Map<String,Object> map);

    List<UniversityBatchNumberPojo> selectUniversityBatchNumber(Map<String,Object> map);

    List<MajorPojo> selectMajorList(Map<String, Object> map);
    Integer selectMajorListCount(Map<String, Object> map);

    /**
     * 统计各院校专业课程情况
     *
     * @return
     */
    List<SelSubjectNumberPojo> getMajorSubStatistics();

    /**
     * 查询各专业薪资排名
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<MajoredDto> getMajorSalary(int pageNo,int pageSize);

    List<UniversityOrMajorPojo> selectMajorByWords(String queryValue);

    SelMajorPojo selectMajorById(String majorId);
}
