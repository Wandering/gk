package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.SortMajorDiffForList;
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

import java.util.*;

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

//        for (){
//
//        }


        for (int i = 0; i < universityInfo1.size(); i++) {
            Map<String, Object> map1 = universityInfo1.get(i);
            //这里获取列表1的universityId
            Long universityId1 = map1.get("universityId") == null ? null : Long.valueOf(map1.get("universityId").toString());
            //判断是否存在majorDiffCompareRtn对象 存在的话在列表中返回,不存在的话创建一个返回
            majorDiffCompareRtn = getMajorDiffCompareRtn(universityId1, majorDiffCompareRtns);
            //判断是不是新对象如果是新对象给对象赋值,如果不是新对象跳过赋值阶段
            if (majorDiffCompareRtn.getUniversityId() == null) {
                setUnivInfo(majorDiffCompareRtn, universityId1, map1);
            }
            LOGGER.info("当前拼装学校:" + map1.get("universityName"));
            //这个对象用来保存院校的专业信息LIST
            List<UniversityMajorInfo> universityMajorInfos;
            for (int j = 0; j < universityInfo2.size(); j++) {
                Map<String, Object> map2 = universityInfo2.get(j);
                //这里获取列表2的universityId
                Long universityId2 = map2.get("universityId") == null ? null : Long.valueOf(map2.get("universityId").toString());
                //如果跟上层循环的院校ID一致,认定当前对象是和上层院校同一个院校,将专业信息存入universityMajorInfos中并将对象放入majorDiffCompareRtn对象中
                if (universityId1.compareTo(universityId2) == 0) {
                    //判断在对象majorDiffCompareRtn中是否有universityMajorInfos列表,如果存在就讲原来的取出来,如果不存在,就NEW一个
                    if (majorDiffCompareRtn.getUniversityMajorInfos2() == null) {
                        universityMajorInfos = new ArrayList<>();
                    } else {
                        universityMajorInfos = majorDiffCompareRtn.getUniversityMajorInfos2();
                    }
                    //设置专业信息到universityMajorInfos中
                    setMajorDiffCompareRtn(universityMajorInfos, map2);
                    majorDiffCompareRtn.setUniversityMajorInfos2(universityMajorInfos);
                    //删除右边list中的数据
                    universityInfo2.remove(map2);
                }
            }
            //判断在对象majorDiffCompareRtn中是否有universityMajorInfos列表,如果存在就讲原来的取出来,如果不存在,就NEW一个
            if (majorDiffCompareRtn.getUniversityMajorInfos1() == null) {
                universityMajorInfos = new ArrayList<>();
            } else {
                universityMajorInfos = majorDiffCompareRtn.getUniversityMajorInfos1();
            }

            setMajorDiffCompareRtn(universityMajorInfos, map1);
            majorDiffCompareRtn.setUniversityMajorInfos1(universityMajorInfos);
            addMajorDiffCompareRtn(majorDiffCompareRtn, majorDiffCompareRtns);
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
            setUnivInfo(majorDiffCompareRtn, universityId1, map);
            setMajorDiffCompareRtn(universityMajorInfos, map);
            majorDiffCompareRtn.setUniversityMajorInfos2(universityMajorInfos);
            addMajorDiffCompareRtn(majorDiffCompareRtn, majorDiffCompareRtns);
        }

        SortMajorDiffForList sortMajorDiffForList = new SortMajorDiffForList();
        Collections.sort(majorDiffCompareRtns, sortMajorDiffForList);
        return majorDiffCompareRtns;
    }


    private void setUnivInfo(MajorDiffCompareRtn majorDiffCompareRtn, Long universityId, Map<String, Object> map) {
        majorDiffCompareRtn.setUniversityId(universityId);
        majorDiffCompareRtn.setUniversityName(map.get("universityName") == null ? null : map.get("universityName").toString());
        majorDiffCompareRtn.setAreaId(map.get("areaId") == null ? null : map.get("areaId").toString());
        majorDiffCompareRtn.setAreaName(map.get("areaName") == null ? null : map.get("areaName").toString());
        majorDiffCompareRtn.setIsCurrArea(Integer.valueOf(map.get("isCurrArea").toString()));
        majorDiffCompareRtn.setRank(map.get("rank") == null ? null : Integer.valueOf(map.get("rank").toString()));
    }

    /**
     * 给MajorDiffCompareRtn设值
     * @param universityMajorInfos
     * @param map
     */
    private void setMajorDiffCompareRtn(List<UniversityMajorInfo> universityMajorInfos,
                                        Map<String, Object> map) {
        String majorCode = map.get("majorCode") == null ? null : map.get("majorCode").toString();
        UniversityMajorInfo universityMajorInfo = new UniversityMajorInfo();
        universityMajorInfo.setBacth(map.get("batch") == null ? null : Integer.valueOf(map.get("batch").toString()));
        universityMajorInfo.setBacthName(map.get("batchName") == null ? null : map.get("batchName").toString());
        universityMajorInfo.setMajorName(map.get("majorName") == null ? null : map.get("majorName").toString());
        universityMajorInfo.setMajorCode(majorCode);
        universityMajorInfos.add(universityMajorInfo);
    }

    /**
     * 获取MajorDiffCompareRtn对象
     * @param universityId1
     * @param majorDiffCompareRtns
     * @return
     */
    private MajorDiffCompareRtn getMajorDiffCompareRtn(Long universityId1, List<MajorDiffCompareRtn> majorDiffCompareRtns) {
        for (MajorDiffCompareRtn majorDiffCompareRtn : majorDiffCompareRtns) {
            if (universityId1.compareTo(majorDiffCompareRtn.getUniversityId()) == 0) {
                return majorDiffCompareRtn;
            }
        }
        return new MajorDiffCompareRtn();

    }

    /**
     * 给majorDiffCompareRtns add值,防止重复add
     * @param majorDiffCompareRtn
     * @param majorDiffCompareRtns
     */
    private void addMajorDiffCompareRtn(MajorDiffCompareRtn majorDiffCompareRtn, List<MajorDiffCompareRtn> majorDiffCompareRtns) {
        for (MajorDiffCompareRtn mm : majorDiffCompareRtns) {
            if (mm.getUniversityId().compareTo(majorDiffCompareRtn.getUniversityId()) == 0) {
                return;
            }
        }
        majorDiffCompareRtns.add(majorDiffCompareRtn);
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
        Collections.copy(list1,universityInfos1);
        Collections.copy(list2,universityInfos2);
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
        map.put("type", universityType);
        List<Map<String, Object>> list = adviceCourseDAO.getUniversityDiffByCourse(map);
        return list;
    }

}
