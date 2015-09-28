package cn.thinkjoy.gk.controller.appraisal;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.controller.appraisal.bean.AppraisalBean;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Controller
@Scope("prototype")
@RequestMapping(value="/question")
public class QuestionController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(QuestionController.class);

    public static void main(String[] args){

        AppraisalBean appraisalBean = new AppraisalBean();

        appraisalBean.setTesterId("11111");

        appraisalBean.setTesterNm("test");

//        System.out.println();

        Map<String,Object> params = new HashMap<>();

        params.put("value",JSON.toJSONString(appraisalBean));

        try {
            System.out.println(HttpRequestUtil.doHttpPost("http://openapi.lstest.com/gkzyv11/", params).split("\\"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

