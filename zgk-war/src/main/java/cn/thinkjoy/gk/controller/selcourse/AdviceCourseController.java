package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAdviceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 16/7/26.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/advice")
public class AdviceCourseController {

    @Autowired
    private IAdviceCourseService adviceCourseService;

    /**
     * 通过所选课程统计可选专业各批次分布(柱状图)
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @RequestMapping(value = "/majorBatchCompare", method = RequestMethod.GET)
    @ResponseBody
    public Object majorBatchCompare(@RequestParam String subjects1, @RequestParam String subjects2) {

        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        MajorBatchCompareRtnPojo majorBatchCompareRtnPojo;
        sort(s1, s2);
        //取出数据
        majorBatchCompareRtnPojo = adviceCourseService.getMajorBatchCompare(s1, s2);
        return majorBatchCompareRtnPojo;
    }

    /**
     * 通过所选课程组合比较两种组合院校差异(表格)
     *
     * @return
     */
    @RequestMapping(value = "/majorDiffCompare", method = RequestMethod.GET)
    @ResponseBody
    public Object majorDiffCompare(@RequestParam String subjects1, @RequestParam String subjects2,
                                   @RequestParam(required = false) Integer batch,
                                   @RequestParam(required = false) Long areaId,
                                   @RequestParam(required = false) Integer universityType) {
        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        sort(s1, s2);
        //取出数据
        List<MajorDiffCompareRtn> majorDiffCompareRtns = adviceCourseService.getMajorDiffCompare(
                s1,
                s2,
                areaId,
                batch,
                universityType);

        return majorDiffCompareRtns;
    }

    /**
     * 查询所选课程院校锁包含的省份(筛选条件)
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @RequestMapping(value = "/queryArea", method = RequestMethod.GET)
    @ResponseBody
    public Object queryArea(@RequestParam String subjects1, @RequestParam String subjects2
    ) {
        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        sort(s1, s2);
        //取出数据
        List<Map<String, Object>> maps = adviceCourseService.queryArea(s1, s2);

        return maps;
    }


    /**
     * 查询所选课程院校锁包含的批次(筛选器筛选条件)
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @RequestMapping(value = "/queryBatch", method = RequestMethod.GET)
    @ResponseBody
    public Object queryBatch(@RequestParam String subjects1, @RequestParam String subjects2) {
        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        sort(s1, s2);
        //取出数据
        List<Map<String, Object>> maps = adviceCourseService.queryBatch(s1, s2);

        return maps;
    }

    /**
     * 查询所选课程院校锁包含的院校类型(筛选器筛选条件)
     *
     * @param subjects1
     * @param subjects2
     * @return
     */
    @RequestMapping(value = "/queryUnivType", method = RequestMethod.GET)
    @ResponseBody
    public Object queryUnivType(@RequestParam String subjects1, @RequestParam String subjects2) {
        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        sort(s1, s2);
        //取出数据
        List<Map<String, Object>> maps = adviceCourseService.queryUnivType(s1, s2);

        return maps;
    }


    private void sort(String[] subjects1, String[] subjects2) {
        //对课程排序  甄别是否所选课程一致
        Arrays.sort(subjects1);
        Arrays.sort(subjects2);
        //假如哈希一致  说明两个数组相等 抛异常
        if (subjects1.hashCode() == subjects2.hashCode())
            throw new BizException(ERRORCODE.SELECT_SUBJECT_IDENTICAL.getCode(), ERRORCODE.SELECT_SUBJECT_IDENTICAL.getMessage());
    }

}
