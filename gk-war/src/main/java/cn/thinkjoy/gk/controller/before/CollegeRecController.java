package cn.thinkjoy.gk.controller.before;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.pojo.JsonPojo;
import cn.thinkjoy.gk.pojo.ResultDataPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yhwang on 15/9/28.
 */
@Controller
@RequestMapping("/before/collegeRecommend")
public class CollegeRecController extends BaseController{

    @RequestMapping(value = "getCollegeList",method = RequestMethod.GET)
    @ResponseBody
    public ResultDataPojo GetCollegeList(){
        String m_aggregateScore = request.getParameter("m_aggregateScore");
        String m_batch = request.getParameter("m_batch");
        String m_kelei = request.getParameter("m_kelei");
        if(StringUtils.isBlank(m_aggregateScore) || StringUtils.isBlank(m_batch) || StringUtils.isBlank(m_kelei)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        JsonPojo jsonPojo = new JsonPojo();

        String url = null;
        try {
            url ="http://sn.gaokao360.gkzy114.com/index.php?s=Restful/CollegeReco/GetCollegeList/m_aggregateScore/"+m_aggregateScore+"/m_batch/"+new String(m_batch.getBytes("ISO-8859-1"),"UTF-8")+"/m_kelei/"+new String(m_kelei.getBytes("ISO-8859-1"),"UTF-8");

        }catch (Exception e){
        }

        try {
            String resultData = HttpRequestUtil.doGet(url);
            if(StringUtils.isBlank(resultData)){
                throw new BizException(ERRORCODE.RESTFUL_INTERFACE_ISNULL.getCode(),ERRORCODE.RESTFUL_INTERFACE_ISNULL.getMessage());
            }
            jsonPojo = JsonMapper.buildNormalMapper().fromJson(resultData,JsonPojo.class);

        }catch (Exception E){
                throw new BizException(ERRORCODE.RESTFUL_INTERFACE_ISERROR.getCode(),ERRORCODE.RESTFUL_INTERFACE_ISERROR.getMessage());

        }
        return jsonPojo.getResult();
    }

}
