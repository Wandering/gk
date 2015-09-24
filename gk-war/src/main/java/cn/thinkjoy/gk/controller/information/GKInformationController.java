package cn.thinkjoy.gk.controller.information;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.Information;
import cn.thinkjoy.gk.service.IInformationService;
import cn.thinkjoy.gk.util.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Controller
@RequestMapping(value = "/gkinformation")
public class GKInformationController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(GKInformationController.class);
    @Autowired
    private IInformationService informationService;

    /**
     *
     * 所有高考热点信息
     * @return
     */
    @RequestMapping(value = "getAllInformation",method = RequestMethod.GET)
    @ResponseBody
    public List<Information> getAllInformation(){
        String pageNo = HttpUtil.getParameter(request, "pageNo", "0");                 //页数
        String pageSize = HttpUtil.getParameter(request, "pageSize", "4");            //每页显示4条信息
        List<Information> information = informationService.getAllInformation( Integer.valueOf(pageNo)*Integer.valueOf(pageSize),Integer.valueOf(pageSize));
        return information;
    }

    /**
     *
     * 根据关键字查询热点信息
     * @return
     */
    @RequestMapping(value = "getInformationByKey",method = RequestMethod.GET)
    @ResponseBody
    public List<Information> getInformationByKey(HttpServletRequest request){
        String key = request.getParameter("key");
        List<Information> information = informationService.getInformationByKey(key);
        return information;
    }

}
