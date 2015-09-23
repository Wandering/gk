package cn.thinkjoy.gk.controller.info;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.UserInfo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zuohao on 15/9/23.
 */
@Controller
@RequestMapping("/info")
public class InfoController extends BaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(InfoController.class);

    @Autowired
    private IUserInfoExService userInfoExService;

    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public UserInfo getUserInfo() {
        UserAccountPojo userAccountPojo=getUserAccountPojo();
        UserInfo userInfo=userInfoExService.findUserInfoById(userAccountPojo.getId());
        return userInfo;
    }

    @RequestMapping(value = "updateUserInfo")
    @ResponseBody
    public String updateUserInfo(UserInfo userInfo){
        try {
            userInfoExService.updateUserInfoById(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
