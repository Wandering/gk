package cn.thinkjoy.gk.controller.information;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.domain.GkinformationGkhot;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IGkinformationGkhotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/gkinformation")
public class GKInformationController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(GKInformationController.class);

    @Autowired
    private IGkinformationGkhotService informationService;


    @RequestMapping(value = "getAllInformation",method = RequestMethod.GET)
    @ResponseBody
    public List<GkinformationGkhot> getAllInformation(HttpServletRequest request) throws Exception{
        long areaId=getAreaCookieValue();
        String pn = request.getParameter("pageNo");
        if (pn == null||pn.length() < 0) {
            pn = "0";
        }
        String ps = request.getParameter("pageSize");
        if (ps == null||ps.length() < 0){
            ps="4";
        }
        List<GkinformationGkhot> information = informationService.getAllInformation(areaId,Integer.valueOf(pn) * Integer.valueOf(ps), Integer.valueOf(ps));
        return information;

    }

    @RequestMapping(value = "getInformationByKey",method = RequestMethod.GET)
    @ResponseBody
    public List<GkinformationGkhot> getInformationByKey(HttpServletRequest request) throws Exception {
        long areaId=getAreaCookieValue();
        String key = request.getParameter("key");
//        String keyString = new String(key.getBytes("ISO-8859-1"),"UTF-8");
        String pn = request.getParameter("pageNo");
        if (pn == null||pn.length() < 0) {
            pn = "0";   //���û������ҳ�룬Ĭ�ϵ�һҳ
        }
        String ps = request.getParameter("pageSize");
        if (ps == null||ps.length() < 0){
            ps="4";  //���û���趨��Ĭ����ʾ4�����
        }
        List<GkinformationGkhot> information = informationService.getInformationByKey(areaId,key, (Integer.valueOf(pn) - 1) * Integer.valueOf(ps), Integer.valueOf(ps));
        return information;
    }

    @RequestMapping(value = "getInformationContentById",method = RequestMethod.GET)
    @ResponseBody
    public GkinformationGkhot getInformationContextById(@RequestParam(value="id",required=false) Integer id) {
        GkinformationGkhot information = informationService.getInformationContentById(id);
        return information;
    }

    @RequestMapping(value="getHotInformation",method=RequestMethod.GET)
    @ResponseBody
    public  List<GkinformationGkhot> getHotInformation(HttpServletRequest request) throws Exception{
        long areaId=getAreaCookieValue();
        String pn = request.getParameter("pageNo");
        if(pn == null || pn.length() < 0){
            pn = "0";
        }
        String ps = request.getParameter("pageSize");
        if (ps == null|| ps.length() < 0){
            ps = "4";
        }
        List<GkinformationGkhot> information = informationService.getHotInformation(areaId,Integer.valueOf(pn) * Integer.valueOf(ps), Integer.valueOf(ps));
        return  information;
    }

    @RequestMapping(value = "updateHotCount",method = RequestMethod.GET)
    @ResponseBody
    public boolean updateHotCount(@RequestParam(value = "id",required = false)Integer id) {
        boolean flag = false;
        try {
            informationService.updateHotInformation(id);
            flag = true;
        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
        }
        return flag;
    }
}
