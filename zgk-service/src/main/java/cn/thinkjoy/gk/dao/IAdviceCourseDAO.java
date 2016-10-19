package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.MajorBatchCompareRtn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/19.
 */
public interface IAdviceCourseDAO {
    /**
     * 获取课程组合对应的专业批次信息
     * 课程组合中只要有一个科目对应就要取这个专业
     * 所有可选专业结果为不限的都需要统计到该组合中
     *
     * @return
     */
    List<MajorBatchCompareRtn> getMajorBatchByCourse(Map<String, Object> map);

    /**
     * 获取差异化课程对应院校专业列表
     *
     * @return
     */
    List<Map<String, Object>> getUniversityDiffByCourse(Map<String, Object> map);


    /**
     * 根据当前选课查询用户所能获取到的省份
     *
     * @return
     */
    List<Map<String, Object>> queryArea(Map<String, Object> map);

    /**
     * 根据用户当前选课查询额用户所能获取到的批次
     *
     * @return
     */
    List<Map<String, Object>> queryBatch(Map<String, Object> map);

    /**
     * 根据用户选课查询用户所能获取到的院校类型
     *
     * @return
     */
    List<Map<String, Object>> queryUnivType(Map<String, Object> map);


}
