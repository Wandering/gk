package cn.thinkjoy.gk.controller.appraisal;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.VerificationKeyConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yhwang on 15/9/28.
 */
@Controller
@Scope("prototype")
@RequestMapping("/before/collegeRecommend")
public class CollegeRecController extends BaseController {

    @RequestMapping(value = "getCollegeList",method = RequestMethod.GET)
    @ResponseBody
    public String GetCollegeList(@RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                                   @RequestParam(value="m_batch",required=false) String m_batch,
                                           @RequestParam(value="m_kelei",required=false) String m_kelei,
                                           @RequestParam(value="id",required=false) String id){
        if(StringUtils.isBlank(m_aggregateScore)
                || StringUtils.isBlank(m_batch)
                || StringUtils.isBlank(m_kelei)
                || StringUtils.isBlank(id)){
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

        Object resultId = session.getAttribute(VerificationKeyConst.COLLEGE_RECOMMENDATION+value);

        if(resultId==null){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        if(!resultId.toString().equals(id.toUpperCase())){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        session.removeAttribute(VerificationKeyConst.COLLEGE_EVALUATION+value);

//        JsonPojo jsonPojo = new JsonPojo();

        String url = null;
        try {
            url ="http://sn.gaokao360.gkzy114.com/index.php?s=Restful/CollegeReco/GetCollegeList/m_aggregateScore/"+m_aggregateScore+"/m_batch/"+m_batch+"/m_kelei/"+m_kelei;

        }catch (Exception e){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),ERRORCODE.PARAM_ERROR.getMessage());
        }

        StringBuffer returnStr = new StringBuffer();

        try {
            String resultData = HttpRequestUtil.doGet(url);
            if(StringUtils.isBlank(resultData)){
                throw new BizException(ERRORCODE.RESTFUL_INTERFACE_ISNULL.getCode(),ERRORCODE.RESTFUL_INTERFACE_ISNULL.getMessage());
            }
//            jsonPojo = JsonMapper.buildNormalMapper().fromJson(resultData,JsonPojo.class);

            returnStr.append(resultData);
        }catch (Exception E){
                throw new BizException(ERRORCODE.RESTFUL_INTERFACE_ISERROR.getCode(),ERRORCODE.RESTFUL_INTERFACE_ISERROR.getMessage());

        }
        return returnStr.toString();
    }

}
