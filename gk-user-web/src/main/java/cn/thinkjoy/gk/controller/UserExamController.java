package cn.thinkjoy.gk.controller;


import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zuohao on 15/9/23.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/exam")
public class UserExamController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserExamController.class);

    @Autowired
    private IUserExamService userExamService;

    /**
     * 查询显示个人信息
     * @return
     */
    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo() {
//        String id=getCookieValue();
//        UserInfo userInfo=userInfoExService.findUserInfoById(Long.valueOf(id));
//        return userInfo;
        return null;
    }

}
