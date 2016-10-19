package cn.thinkjoy.gk.controller.expert;

import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.service.IExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liusven on 2016/10/19.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert")
public class ExpertController
{
    @Autowired
    private IExpertService expertService;


}
