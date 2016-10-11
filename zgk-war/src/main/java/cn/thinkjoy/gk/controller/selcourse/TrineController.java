package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.MajorDiffCompareRtn;
import cn.thinkjoy.gk.pojo.MajorBatchCompareRtnPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAdviceCourseService;
import cn.thinkjoy.gk.service.ITrineService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import cn.thinkjoy.zgk.domain.BizData4Page;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
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
@RequestMapping(value = "/trine")
public class TrineController extends BaseCommonController{

    @Autowired
    private ITrineService trineService;

    /**
     * 三位一体自主招生
     * @return
     */
    @RequestMapping(value = "/getTrine", method = RequestMethod.GET)
    @ResponseBody
    public Object insertEvaluation(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer rows,
                                   @RequestParam(required = false) Long areaId,
                                   @RequestParam(required = false) String majorType,
                                   @RequestParam(required = false) String batch,
                                   @RequestParam(required = false) String universityName,
                                   @RequestParam(required = false) Integer year) {


        Map<String,Object> conditions = new HashedMap();
        conditions.put("areaId",areaId);
        conditions.put("majorType",majorType);
        conditions.put("batch",batch);
        conditions.put("universityName",universityName);
        conditions.put("year",year);
        conditions.put("offset",(page-1)*rows);
        conditions.put("rows",rows);
        List mainData = trineService.queryPage(conditions);
        int records = trineService.count(conditions);
        BizData4Page bizData4Page = new BizData4Page();
        bizData4Page.setRows(mainData);
        bizData4Page.setPage(page);
        bizData4Page.setRecords(records);
        int total = records / rows;
        int mod = records % rows;
        int pagesize=0;
        if(mainData!=null) {
            pagesize = mainData.size();
        }
        if(mod > 0){
            total = total + 1;
        }
        bizData4Page.setPagesize(pagesize);
        bizData4Page.setTotal(total);

        return bizData4Page;
    }

    /**
     * 三位一体自主招生查询年份
     * @return
     */
    @RequestMapping(value = "/getYear", method = RequestMethod.GET)
    @ResponseBody
    public Object getYear(){

        return trineService.queryYear();
    }


    /**
     * 三位一体自主招生查询科类
     * @return
     */
    @RequestMapping(value = "/getMajorType", method = RequestMethod.GET)
    @ResponseBody
    public Object getMajorType(){
        return trineService.queryMajorType();
    }


    /**
     * 三位一体自主招生查询批次
     * @return
     */
    @RequestMapping(value = "/getBatch", method = RequestMethod.GET)
    @ResponseBody
    public Object getBatch(){
        return trineService.queryBatchName();
    }
}
