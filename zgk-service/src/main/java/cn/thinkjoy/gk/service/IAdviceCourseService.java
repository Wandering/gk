package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/19.
 */
public interface IAdviceCourseService {
    /**
     * 获取两个课程批次比较结果
     * 这里采用的是16年招生计划批次
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    MajorBatchCompareRtnPojo getMajorBatchCompare(String[] subjects1, String[] subjects2);


    /**
     * 过去两组选课的差异化比较结果
     * 以学校为一组
     * 只展示差异化结果
     * 排序顺序为 本地院校外地院校(内部排序为院校排名)
     * 专业名称和批次无次序
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    List<MajorDiffCompareRtn> getMajorDiffCompare(String[] subjects1, String[] subjects2,
                                                  Long areaId, Integer batch, Integer universityType);

    List<Map<String, Object>> queryArea(String[] subjects1, String[] subjects2);

    List<Map<String, Object>> queryBatch(String[] subjects1, String[] subjects2);

    List<Map<String, Object>> queryUnivType(String[] subjects1, String[] subjects2);


}
