package cn.thinkjoy.gk.controller.appraisal;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.appraisal.bean.AppraisalBean;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/lstest",method = RequestMethod.POST)
    @ResponseBody
    public String lstest() throws Exception{

        UserAccountPojo userAccountPojo =  getUserAccountPojo();

        AppraisalBean appraisalBean = new AppraisalBean();

        appraisalBean.setTesterId(userAccountPojo.getId());

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
            returnStr.append(obj.getString("data").replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

}

