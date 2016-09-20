package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IAdviceCourseDAO;
import cn.thinkjoy.gk.domain.MajorBatchCompareRtn;
import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.domain.UniversityMajorInfo;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;
import cn.thinkjoy.gk.service.IAdviceCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/9/19.
 */
@Service("AdviceCourseServiceImpl")
public class AdviceCourseServiceImpl implements IAdviceCourseService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdviceCourseServiceImpl.class);
    private static long CURR_AREAID = 330000;
    private static long CURR_YEAR = 2016;
    @Autowired
    private IAdviceCourseDAO adviceCourseDAO;


    @Override
    public List<Map<String, Object>> queryArea(String[] subjects1, String[] subjects2) {
        Map<String, Object> map = new HashedMap();
        map.put("subjects1", subjects1);
        map.put("subjects2", subjects2);
        return adviceCourseDAO.queryArea(map);
    }

    @Override
    public List<Map<String, Object>> queryBatch(String[] subjects1, String[] subjects2) {
        Map<String, Object> map = new HashedMap();
        map.put("subjects1", subjects1);
        map.put("subjects2", subjects2);
        map.put("currAreaId", CURR_AREAID);
        map.put("year", CURR_YEAR);
        return adviceCourseDAO.queryBatch(map);
    }

    @Override
    public List<Map<String, Object>> queryUnivType(String[] subjects1, String[] subjects2) {
        Map<String, Object> map = new HashedMap();
        map.put("subjects1", subjects1);
        map.put("subjects2", subjects2);
        return adviceCourseDAO.queryUnivType(map);
    }

    /**
     * 获取两个课程批次比较结果
     * 这里采用的是16年招生计划批次
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @Override
    public MajorBatchCompareRtnPojo getMajorBatchCompare(String[] subjects1, String[] subjects2) {
        MajorBatchCompareRtnPojo majorBatchCompareRtnPojo = new MajorBatchCompareRtnPojo();

        //获取选课1的批次结果
        List<MajorBatchCompareRtn> majorBatchCompareRtns1 = getMajorBatchByCourse(subjects1);
        //获取选课2的批次结果
        List<MajorBatchCompareRtn> majorBatchCompareRtns2 = getMajorBatchByCourse(subjects2);

        majorBatchCompareRtnPojo.setMajorBatchCompareRtns1(majorBatchCompareRtns1);
        majorBatchCompareRtnPojo.setMajorBatchCompareRtns2(majorBatchCompareRtns2);

        return majorBatchCompareRtnPojo;
    }


    /**
     * 获取两组选课的差异化比较结果
     * 每一个学校为一组
     * 只展示差异化结果
     * 排序顺序为 本地院校外地院校(内部排序为院校排名)
     * 专业名称和批次无次序
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @Override
    public List<MajorDiffCompareRtn> getMajorDiffCompare(String[] subjects1, String[] subjects2,
                                                         Long areaId, Integer batch, Integer universityType) {
        //获取选课组合1院校信息
        List<Map<String, Object>> universityInfo1 = getUniversityInfo(subjects1, areaId, batch, universityType);
        //获取选课组合2院校信息
        List<Map<String, Object>> universityInfo2 = getUniversityInfo(subjects2, areaId, batch, universityType);

        List<MajorDiffCompareRtn> treeUniversity = treeUniversity(universityInfo1, universityInfo2);

        return treeUniversity;
    }


    /**
     * 获取课程组合对应的专业批次信息
     * 课程组合中只要有一个科目对应就要取这个专业
     * 所有可选专业结果为不限的都需要统计到该组合中
     *
     * @param subjects
     * @return
     */
    private List<MajorBatchCompareRtn> getMajorBatchByCourse(String[] subjects) {
        Map<String, Object> map = new HashedMap();
        map.put("subjects", subjects);
        map.put("currAreaId", CURR_AREAID);
        map.put("year", CURR_YEAR);
        List<MajorBatchCompareRtn> list = adviceCourseDAO.getMajorBatchByCourse(map);
        return list;
    }


    /**
     * 将获取到的院校信息转换成树形结构
     * 以左边为基准将对齐的院校置入domain中
     * 同时在原有的list中删除该条数据(避免重复插入)
     * 最终将右边没有插入的全部置入domain中
     *
     * @param universityInfo1
     * @param universityInfo2
     * @return
     */
    private List<MajorDiffCompareRtn> treeUniversity(List<Map<String, Object>> universityInfo1, List<Map<String, Object>> universityInfo2) {
        MajorDiffCompareRtn majorDiffCompareRtn;
        List<MajorDiffCompareRtn> majorDiffCompareRtns = new ArrayList<>();
        //对院校列表进行去重
        removeRepeat(universityInfo1, universityInfo2);

        for (int i = 0; i < universityInfo1.size(); i++) {
            Map<String, Object> map1 = universityInfo1.get(i);
            Long universityId1 = map1.get("universityId") == null ? null : Long.valueOf(map1.get("universityId").toString());
            majorDiffCompareRtn = getMajorDiffCompareRtn(universityId1, majorDiffCompareRtns);
            LOGGER.info("当前拼装学校:" + map1.get("universityName"));
            List<UniversityMajorInfo> universityMajorInfos;
            for (int j = 0; j < universityInfo2.size(); j++) {
                Map<String, Object> map2 = universityInfo2.get(j);
                Long universityId2 = map2.get("universityId") == null ? null : Long.valueOf(map2.get("universityId").toString());

                /*
                判断  如果院校id相等  那么 将所有院校信息放入院校domain 专业信息放入专业domain
                */

                if (universityId1.compareTo(universityId2) == 0) {
                    //先做判断  如果专业相等 跳过当前轮
                    if (majorDiffCompareRtn.getUniversityMajorInfos2() == null) {
                        universityMajorInfos = new ArrayList<>();
                    } else {
                        universityMajorInfos = majorDiffCompareRtn.getUniversityMajorInfos2();
                    }
                    setMajorDiffCompareRtn(universityMajorInfos, majorDiffCompareRtn, map2, universityId1);
                    majorDiffCompareRtn.setUniversityMajorInfos2(universityMajorInfos);
                    //删除右边list中的数据
                    universityInfo2.remove(map2);
                }
            }
            //无论如何该该数据都会被置入UniversityMajorInfos1中
            if (majorDiffCompareRtn.getUniversityMajorInfos1() == null) {
                universityMajorInfos = new ArrayList<>();
            } else {
                universityMajorInfos = majorDiffCompareRtn.getUniversityMajorInfos1();
            }
            setMajorDiffCompareRtn(universityMajorInfos, majorDiffCompareRtn, map1, universityId1);
            majorDiffCompareRtn.setUniversityMajorInfos1(universityMajorInfos);
            majorDiffCompareRtns.add(majorDiffCompareRtn);
        }
        //将右边未放入的项放入domain
        for (Map<String, Object> map : universityInfo2) {
            List<UniversityMajorInfo> universityMajorInfos;
            majorDiffCompareRtn = new MajorDiffCompareRtn();
            if (majorDiffCompareRtn.getUniversityMajorInfos1() == null) {
                universityMajorInfos = new ArrayList<>();
            } else {
                universityMajorInfos = majorDiffCompareRtn.getUniversityMajorInfos1();
            }
            Long universityId1 = map.get("universityId") == null ? null : Long.valueOf(map.get("universityId").toString());
            setMajorDiffCompareRtn(universityMajorInfos, majorDiffCompareRtn, map, universityId1);
            majorDiffCompareRtn.setUniversityMajorInfos2(universityMajorInfos);
            majorDiffCompareRtns.add(majorDiffCompareRtn);
        }
        return majorDiffCompareRtns;
    }

    private void setMajorDiffCompareRtn(List<UniversityMajorInfo> universityMajorInfos,
                                        MajorDiffCompareRtn majorDiffCompareRtn,
                                        Map<String, Object> map,
                                        Long universityId) {
        String majorCode = map.get("majorCode") == null ? null : map.get("majorCode").toString();
        majorDiffCompareRtn.setUniversityId(universityId);
        majorDiffCompareRtn.setUniversityName(map.get("universityName") == null ? null : map.get("universityName").toString());
        majorDiffCompareRtn.setAreaId(map.get("areaId") == null ? null : map.get("areaId").toString());
        majorDiffCompareRtn.setAreaName(map.get("areaName") == null ? null : map.get("areaName").toString());

        UniversityMajorInfo universityMajorInfo = new UniversityMajorInfo();
        universityMajorInfo.setBacth(map.get("batch") == null ? null : Integer.valueOf(map.get("batch").toString()));
        universityMajorInfo.setBacthName(map.get("batchName") == null ? null : map.get("batchName").toString());
        universityMajorInfo.setMajorName(map.get("majorName") == null ? null : map.get("majorName").toString());

        universityMajorInfo.setMajorCode(majorCode);
        universityMajorInfos.add(universityMajorInfo);
    }

    private MajorDiffCompareRtn getMajorDiffCompareRtn(Long universityId1, List<MajorDiffCompareRtn> majorDiffCompareRtns) {
        for (MajorDiffCompareRtn majorDiffCompareRtn : majorDiffCompareRtns) {
            if (universityId1.compareTo(majorDiffCompareRtn.getUniversityId()) == 0) {
                return majorDiffCompareRtn;
            }
        }
        return new MajorDiffCompareRtn();

    }

    /**
     * 对院校列表进行去重
     *
     * @param universityInfos1
     * @param universityInfos2
     */
    private void removeRepeat(List<Map<String, Object>> universityInfos1, List<Map<String, Object>> universityInfos2) {
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();
        list1.addAll(universityInfos1);
        list2.addAll(universityInfos2);
        universityInfos1.removeAll(list2);
        universityInfos2.removeAll(list1);
    }


    /**
     * 根据所选课程和地区,批次,院校类型查询院校信息
     *
     * @param subjects
     * @param areaId
     * @param batch
     * @param universityType
     * @return
     */
    private List<Map<String, Object>> getUniversityInfo(String[] subjects, Long areaId, Integer batch, Integer universityType) {
        Map<String, Object> map = new HashedMap();
        map.put("subjects", subjects);
        map.put("currAreaId", CURR_AREAID);
        map.put("year", CURR_YEAR);
        map.put("areaId", areaId);
        map.put("batch", batch);
        map.put("universityType", universityType);
        List<Map<String, Object>> list = adviceCourseDAO.getUniversityDiffByCourse(map);
        return list;
    }

}
