package cn.thinkjoy.gk.controller.volunteer;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.appraisal.bean.AppraisalBean;
import cn.thinkjoy.gk.controller.volunteer.bean.ReportBean;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import cn.thinkjoy.gk.util.VerificationKeyConst;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@Scope("prototype")
@RequestMapping(value="/guide")
public class GuideController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(GuideController.class);

    /**
     * 获取批次
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batch",method = RequestMethod.GET)
    @ResponseBody
    public String batch(@RequestParam(value="m_candidateNumber",required=false) String m_candidateNumber,
                        @RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                        @RequestParam(value="m_kelei",required=false) String m_kelei,
                        @RequestParam(value="m_ranking",required=false) String m_ranking) throws Exception{

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        StringBuffer returnStr = new StringBuffer("");
        try {


            String result = HttpRequestUtil.doGet("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetBatch/m_candidateNumber/"+m_candidateNumber+"/m_aggregateScore/"+m_aggregateScore+"/m_ranking/"+m_ranking+"/m_kelei/"+m_kelei);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }
            returnStr.append(obj.toJSONString().replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 获取志愿指导院校
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/school",method = RequestMethod.GET)
    @ResponseBody
    public String school(@RequestParam(value="m_candidateNumber",required=false) String m_candidateNumber,
                        @RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                        @RequestParam(value="m_kelei",required=false) String m_kelei,
                        @RequestParam(value="m_ranking",required=false) String m_ranking,
                        @RequestParam(value="m_batch_id",required=false) String m_batch_id,
                        @RequestParam(value="m_batch",required=false) String m_batch,
                        @RequestParam(value="m_province_id",required=false) String m_province_id,
                        @RequestParam(value="m_province",required=false) String m_province,
                        @RequestParam(value="m_specialty_id",required=false) String m_specialty_id,
                        @RequestParam(value="m_specialty_name",required=false) String m_specialty_name,
                        @RequestParam(value="m_favorites_by_university_codes",required=false) String m_favorites_by_university_codes
            ) throws Exception{

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        StringBuffer returnStr = new StringBuffer("");
        try {
            StringBuffer url = new StringBuffer("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetCollegeList");

            if(!StringUtils.isEmpty(m_candidateNumber)){
                url.append("/m_candidateNumber/"+m_candidateNumber);
            }
            if(!StringUtils.isEmpty(m_aggregateScore)){
                url.append("/m_aggregateScore/"+m_aggregateScore);
            }
            if(!StringUtils.isEmpty(m_ranking)){
                url.append("/m_ranking/"+m_ranking);
            }
            if(!StringUtils.isEmpty(m_kelei)){
                url.append("/m_kelei/"+m_kelei);
            }
            if(!StringUtils.isEmpty(m_batch_id)){
                url.append("/m_batch_id/"+m_batch_id);
            }
            if(!StringUtils.isEmpty(m_batch)){
                url.append("/m_batch/"+m_batch);
            }
            if(!StringUtils.isEmpty(m_province_id)){
                url.append("/m_province_id/"+m_province_id);
            }
            if(!StringUtils.isEmpty(m_province)){
                url.append("/m_province/"+m_province);
            }
            if(!StringUtils.isEmpty(m_specialty_id)){
                url.append("/m_specialty_id/"+m_specialty_id);
            }
            if(!StringUtils.isEmpty(m_specialty_name)){
                url.append("/m_specialty_name/"+m_specialty_name);
            }
            if(!StringUtils.isEmpty(m_favorites_by_university_codes)){
                url.append("/m_favorites_by_university_codes/"+m_favorites_by_university_codes);
            }

            String result = HttpRequestUtil.doGet(url.toString());

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }

            returnStr.append(obj.toJSONString().replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 获取志愿指导院校
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/report",method = RequestMethod.GET)
    @ResponseBody
    public String report(ReportBean reportBean) throws Exception{

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        StringBuffer returnStr = new StringBuffer("");

        try {


            String result = HttpRequestUtil.httpPost("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetReport",JSON.toJSONString(reportBean),false);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }

            returnStr.append(obj.toJSONString().replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

}

