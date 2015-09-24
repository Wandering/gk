package cn.thinkjoy.gk.controller.banner;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.Banner;
import cn.thinkjoy.gk.service.IBannerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by ZL on 2015/9/23.
 */
@Controller
@RequestMapping(value = "/banner")
public class BannerController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private IBannerService bannerService;
    /**
     *
     * 通过type类型拿到跳转页面的url
     * @return
     */
    @RequestMapping(value = "getUrlByType",method = RequestMethod.GET)
    @ResponseBody
    public Banner getUrlByType(HttpServletRequest request){
        String type = request.getParameter("type");
        Integer ty = 0;
        if(StringUtils.isNotBlank(type)){
            ty = Integer.parseInt(type);
        }
        Banner linkUrl = bannerService.getBannerByType(ty);
        return linkUrl;
    }

}
