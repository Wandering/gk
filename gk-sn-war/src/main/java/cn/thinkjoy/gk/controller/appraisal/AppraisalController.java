package cn.thinkjoy.gk.controller.appraisal;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.appraisal.bean.AppraisalBean;
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
@RequestMapping(value="/appraisal")
public class AppraisalController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(AppraisalController.class);

    /**
     * 专业测评
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/lstest",method = RequestMethod.GET)
    @ResponseBody
    public String lstest() throws Exception{

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        AppraisalBean appraisalBean = new AppraisalBean();

        appraisalBean.setTesterId(userAccountPojo.getId()+"gk360");

        appraisalBean.setTesterNm(userAccountPojo.getName());

        Map<String,Object> params = new HashMap<>();

        params.put("value",JSON.toJSONString(appraisalBean));

        StringBuffer returnStr = new StringBuffer("");
        try {
            String result = HttpRequestUtil.doHttpPost("http://openapi.lstest.com/gkzyv11/", params);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }
            returnStr.append(obj.getString("data").replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 院校测评
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/schoolTest",method = RequestMethod.GET)
    @ResponseBody
    public String schoolTest(@RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                             @RequestParam(value="m_university_name",required=false) String m_university_name,
                             @RequestParam(value="m_kelei",required=false) String m_kelei,
                             @RequestParam(value="code",required=false) String code) throws Exception{

        if(StringUtils.isBlank(m_aggregateScore)
                || StringUtils.isBlank(m_university_name)
                || StringUtils.isBlank(m_kelei)
                || StringUtils.isBlank(code)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        Long value = userAccountPojo.getId();

        Object resultCode = session.getAttribute(VerificationKeyConst.COLLEGE_EVALUATION+value);

        if(resultCode==null){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        if(!resultCode.toString().equals(code.toUpperCase())){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        session.removeAttribute(VerificationKeyConst.COLLEGE_EVALUATION+value);

        String returnStr = null;
        try {

            String result = HttpRequestUtil.doGet("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/CollegeEval/GetEvaluation/m_aggregateScore/"+m_aggregateScore+"/m_university_name/"+m_university_name+"/m_kelei/"+m_kelei);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            returnStr = result;

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 获取位次
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findRanking",method = RequestMethod.GET)
    @ResponseBody
    public String findRanking(@RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                              @RequestParam(value="code",required=false) String code) throws Exception{

        if(StringUtils.isBlank(m_aggregateScore)
                || StringUtils.isBlank(code)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        Long value = userAccountPojo.getId();

        Object resultCode = session.getAttribute(VerificationKeyConst.GET_THE_ORDER+value);

        if(resultCode==null){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        if(!resultCode.toString().equals(code.toUpperCase())){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }
        session.removeAttribute(VerificationKeyConst.GET_THE_ORDER+value);

        String returnStr = null;
        try {

            String result = HttpRequestUtil.doGet("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/CandidateRanking/GetRanking/m_aggregateScore/"+m_aggregateScore);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            returnStr = result;

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

}

