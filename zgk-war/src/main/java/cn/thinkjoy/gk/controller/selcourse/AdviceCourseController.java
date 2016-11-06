package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.TimeUtil;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAdviceCourseService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import cn.thinkjoy.zgk.domain.BizData4Page;
import com.alibaba.fastjson.JSON;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyongping on 16/7/26.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/advice")
public class AdviceCourseController {

    @Autowired
    private IAdviceCourseService adviceCourseService;

    private static final RedisRepository redisRepository = RedisUtil.getInstance();

    private static final Long TIME_OUT = 7L;

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
                                   @RequestParam(required = false) Integer universityType,
                                   @RequestParam(required = false,defaultValue = "1") Integer page,
                                   @RequestParam(required = false,defaultValue = "10") Integer rows) {
        //取redis
        String[] s1 = subjects1.split(",");
        String[] s2 = subjects2.split(",");
        sort(s1, s2);
        //取出数据
//        List<MajorDiffCompareRtn> majorDiffCompareRtns = adviceCourseService.getMajorDiffCompare(
//                s1,
//                s2,
//                areaId,
//                batch,
//                universityType);


        return getMajorDiffCompareRtns(s1,s2,areaId,batch,universityType,page,rows);
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


    /**
     * 做缓存分页,每次取对应条数的数据,在第一次读取之后将数据置入缓存
     * @param s1
     * @param s2
     * @param areaId
     * @param batch
     * @param universityType
     * @param page
     * @param rows
     * @return
     */
    private BizData4Page  getMajorDiffCompareRtns(String[] s1,String[] s2,
                                     Long areaId,Integer batch,
                                     Integer universityType,Integer page,Integer rows){

        List<MajorDiffCompareRtn> majorDiffCompareRtns = null;

        String key = getRedisKey(s1,s2,areaId,batch,universityType);

        if(redisRepository.exists(key)){

            majorDiffCompareRtns = JSON.parseObject(redisRepository.get(key).toString(),List.class);
        }else {
            majorDiffCompareRtns = adviceCourseService.getMajorDiffCompare(
                    s1,
                    s2,
                    areaId,
                    batch,
                    universityType);
            //将查询数据放入redis缓存中,下次从缓存中读取数据
            redisRepository.set(key,JSON.toJSONString(majorDiffCompareRtns),TIME_OUT, TimeUnit.DAYS);
        }
        return doPage(majorDiffCompareRtns,page,rows);
    }

    protected BizData4Page doPage(List<MajorDiffCompareRtn> mainData, Integer page, Integer rows){

        int records = mainData.size();
        int total = records / rows;
        int mod = records % rows;
        if(mod > 0){
            total = total + 1;
        }
        BizData4Page bizData4Page = new BizData4Page();
        page = page<=0?1:page;
        //如果当前页大于最大页取最大页数对应的值
        int start = page<total?(page - 1) * rows:records-mod;
        //判断结束值如果大于数组长度,取当前位置到数组最大长度
        int end = ((page - 1) * rows+rows)<records?((page - 1) * rows+rows):records;
        bizData4Page.setRows(mainData.subList(start,end));
        //如果当前页大于最大页取最大页数
        bizData4Page.setPage(page<total?page:total);
        bizData4Page.setRecords(records);

        int pagesize=0;
        if(mainData!=null) {
            pagesize = bizData4Page.getRows().size();
        }

        bizData4Page.setPagesize(pagesize);
        bizData4Page.setTotal(total);

        return bizData4Page;
    }

    private String getRedisKey(String[] s1,String[] s2,Long areaId,Integer batch,Integer universityType){
        String keyString = "";
        for (String s: s1){
            keyString+=s;
        }
        for (String s: s2){
            keyString+=s;
        }
        if (batch!=null)keyString+=batch;
        if (areaId!=null)keyString+=areaId;
        if (universityType!=null)keyString+=universityType;
        return MD5Util.md5String(keyString);

    }
}
