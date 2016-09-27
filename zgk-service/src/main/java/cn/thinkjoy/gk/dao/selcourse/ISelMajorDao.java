package cn.thinkjoy.gk.dao.selcourse;

import cn.thinkjoy.gk.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/19.
 */
public interface ISelMajorDao {

    List<SelSubjectNumberPojo> selectSubjectNumber(Map<String,Object> map);
    List<UniversityBatchNumberPojo> selectUniversityBatchNumber(Map<String,Object> map);
    List<MajorPojo> selectMajorList(Map<String, Object> map);
    Integer selectMajorListCount(Map<String, Object> map);

    /**
     * 根据课程名查询专业总数
     *
     * @param subName
     * @return
     */
    SelSubjectNumberPojo getMajorCountBySub(@Param("subName") String subName);

    /**
     * 查询各专业薪资排名
     *
     * @param index
     * @param pageSize
     * @return
     */
    List<MajoredDto> getMajorSalary(@Param("index") int index,@Param("pageSize") int pageSize);
}
