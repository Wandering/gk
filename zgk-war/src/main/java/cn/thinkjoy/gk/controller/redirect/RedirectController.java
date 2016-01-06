package cn.thinkjoy.gk.controller.redirect;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.ControllerReturnConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value="/")
public class RedirectController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(RedirectController.class);

    /**
     * 重定向
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/redirect",method = RequestMethod.GET)
    public String redirect(@RequestParam(value="params",required=false) String params,
                           @RequestParam(value="url",required=false) String url) throws Exception{

//        UserAccountPojo userAccountPojo = getUserAccountPojo();
//
//        if(userAccountPojo==null){
//            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
//        }
//
//        Integer vipStatus = userAccountPojo.getVipStatus();
//
//        if(vipStatus==null||vipStatus==0){
//            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
//        }

        request.setAttribute("params",params);


        return ControllerReturnConst.REDIRECT+url;
    }

    /**
     * 转发
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/forward",method = RequestMethod.POST)
    public String forward(@RequestParam(value="params",required=false) String params,
                           @RequestParam(value="url",required=false) String url) throws Exception{

//        UserAccountPojo userAccountPojo = getUserAccountPojo();
//
//        if(userAccountPojo==null){
//            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
//        }
//
//        Integer vipStatus = userAccountPojo.getVipStatus();
//
//        if(vipStatus==null||vipStatus==0){
//            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
//        }

        request.setAttribute("params",params);


        return ControllerReturnConst.FORWARD+url;
    }

}

