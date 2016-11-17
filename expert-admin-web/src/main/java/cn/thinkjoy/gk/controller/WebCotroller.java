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
    @RequestMapping("/forgot-password")
    public ModelAndView forgotPassword() {
        return new ModelAndView("/login/forgot-password");
    }

}
