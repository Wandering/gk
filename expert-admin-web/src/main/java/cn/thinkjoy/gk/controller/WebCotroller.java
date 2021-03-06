package cn.thinkjoy.gk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/expert/admin")
public class WebCotroller {
    /**
     * index
     * 首页
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
    /**
     * login
     * 登录
     */
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login/login");
    }
    /**
     * forgot-password
     * 重设面貌
     */
    @RequestMapping("/login/forgot-password")
    public ModelAndView forgotPassword() {
        return new ModelAndView("/login/forgot-password");
    }
    /**
     * info-management
     * 预约信息管理
     */
    @RequestMapping("/info-management")
    public ModelAndView infoManagement() {
        return new ModelAndView("/info-management/info-management");
    }
    /**
     * user-info
     * 用户信息
     */
    @RequestMapping("/user-info")
    public ModelAndView UserInfo() {
        return new ModelAndView("/info-management/user-info");
    }
    /**
     * repot-info
     * 测评报告
     */
    @RequestMapping("/report-info")
    public ModelAndView reportInfo() {
        return new ModelAndView("/info-management/report-info");
    }
    /**
     * user-center
     * 个人中心
     */
    @RequestMapping("/user-center")
    public ModelAndView userCenter() {
        return new ModelAndView("/user-center/user-center");
    }
    /**
     * experts-play
     * 专家播放
     */
    @RequestMapping("/experts-play")
    public ModelAndView expertsPlay() {
        return new ModelAndView("/info-management/experts-play");
    }

}
