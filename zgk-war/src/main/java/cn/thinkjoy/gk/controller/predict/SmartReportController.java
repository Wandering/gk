package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.gk.common.ReportUtil;
import cn.thinkjoy.gk.entity.UniversityInfoView;
import cn.thinkjoy.gk.pojo.BatchView;
import cn.thinkjoy.gk.pojo.SpecialtyView;
import cn.thinkjoy.gk.service.ISystemParmasService;
import cn.thinkjoy.gk.service.IUniversityInfoService;
import cn.thinkjoy.gk.service.IUniversityMajorEnrollingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
@Controller
@RequestMapping("/report")
public class SmartReportController {
    private static final Logger LOGGER= LoggerFactory.getLogger(SmartReportController.class);
    @Resource
    IUniversityInfoService iUniversityInfoService;
    @Resource
    ISystemParmasService iSystemParmasService;
    @Resource
    IUniversityMajorEnrollingService iUniversityMajorEnrollingService;

    /**
     * 获取批次及批次控制线信息
     * @param score
     * @param cate
     * @param province
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/batch")
    @ResponseBody
    public Map<String,Object> report(@RequestParam(value = "score",required = false) Integer score,
                                     @RequestParam(value = "cate",required = false) Integer cate,
                                         @RequestParam(value = "province",required = false) String province,
                                         ModelMap modelMap) {

        LOGGER.info("=======批次及批次控制线信息 Start=======");
        LOGGER.info("分数:" + score);
        LOGGER.info("科类:" + cate);
        LOGGER.info("省份:" + province);
        List<BatchView> batchViews = iSystemParmasService.selectSystemParmas(cate, score, province);
        LOGGER.info("该学生达标:" + batchViews.size() + "个批次");
        Map resultMap = new HashMap();
        resultMap.put("batchViews", batchViews);
        LOGGER.info("=======批次及批次控制线信息 End========");
        return resultMap;
    }

    /**
     * 根绝院校ID获取专业信息
     * @return
     */
    @RequestMapping("/get/specialty")
    @ResponseBody
    public Map<String,Object> getSpecialty(@RequestParam(value = "uId") Integer uId
                                           ,@RequestParam(value = "cate") Integer cate) {
        Map parmasMap = new HashMap();
        parmasMap.put("universityId", uId);
        parmasMap.put("majorType", cate);
        List<SpecialtyView> universityMajoyEnrollingPlans = iUniversityMajorEnrollingService.selectList(parmasMap);
        Map resultMap = new HashMap();
        resultMap.put("specialtys", universityMajoyEnrollingPlans);
        return resultMap;
    }
    /**
     * 智能填报主入口
     * 获取用户  输入分数、位次、文理 过滤院校清单
     * @return
     */
    @RequestMapping("/main")
    @ResponseBody
    public Map<String,Object> reportMain(
                             @RequestParam(value = "batch") Integer batch,
                             @RequestParam(value = "score") Integer score,
                             @RequestParam(value = "province",required = false) String province,
                             @RequestParam(value = "categorie") Integer categorie,
                             ModelMap modelMap) {
        LOGGER.info("=======智能填报主入口 Start=======");
        LOGGER.info("分数:" + score);
        LOGGER.info("科类:" + categorie);
        LOGGER.info("省份:" + province);
        LOGGER.info("批次:" + batch);

        LOGGER.info("==线差计算 Start==");
        //根据分数及控制线 计算线差
        Integer lineDiff = iUniversityInfoService.getLineDiff(batch, score, categorie, province);
        LOGGER.info("线差为:" + lineDiff);
        LOGGER.info("==线差计算 End==");

        String tbName = ReportUtil.getTableName(province, categorie, batch);
        LOGGER.info("tableName:" + tbName);
        Map map = new HashMap<>();
        map.put("tableName", tbName);
        map.put("scoreDiff", lineDiff);
        map.put("province", province);
        List<UniversityInfoView> universityInfoViewList = iUniversityInfoService.selectUniversityInfo(map);
        Map resultMap = new HashMap();
        resultMap.put("universityInfoViewList", universityInfoViewList);
        LOGGER.info("最终输出:" + universityInfoViewList.size() + "个院校清单");
        LOGGER.info("=======智能填报主入口 End=======");
        return resultMap;
    }


}
