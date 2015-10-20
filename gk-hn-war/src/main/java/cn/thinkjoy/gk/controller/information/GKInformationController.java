package cn.thinkjoy.gk.controller.information;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.domain.Information;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Controller
@RequestMapping(value = "/gkinformation")
public class GKInformationController extends BaseCommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GKInformationController.class);
    @Autowired
    private IInformationService informationService;

    /**
     *
     * ���и߿��ȵ���Ϣ
     * @return
     */
    @RequestMapping(value = "getAllInformation",method = RequestMethod.GET)
    @ResponseBody
    public List<Information> getAllInformation(HttpServletRequest request){
        String pn = request.getParameter("pageNo"); //ҳ��
        if (pn == null||pn.length() < 0) {
            pn = "0";   //���û������ҳ�룬Ĭ�ϵ�һҳ
        }
        String ps = request.getParameter("pageSize");            //ÿҳ��ʾ������Ϣ
        if (ps == null||ps.length() < 0){
            ps="4";  //���û���趨��Ĭ����ʾ4�����
        }
        List<Information> information = informationService.getAllInformation(Integer.valueOf(pn) * Integer.valueOf(ps), Integer.valueOf(ps));
        return information;

    }
    /**
     *
     * ��ݹؼ��ֲ�ѯ�ȵ���Ϣ
     * @return
     */
    @RequestMapping(value = "getInformationByKey",method = RequestMethod.GET)
    @ResponseBody
    public List<Information> getInformationByKey(HttpServletRequest request) throws UnsupportedEncodingException {
        String key = request.getParameter("key");                  //��ȡ�ؼ���
//        String keyString = new String(key.getBytes("ISO-8859-1"),"UTF-8");
        String pn = request.getParameter("pageNo"); //ҳ��
        if (pn == null||pn.length() < 0) {
            pn = "0";   //���û������ҳ�룬Ĭ�ϵ�һҳ
        }
        String ps = request.getParameter("pageSize");            //ÿҳ��ʾ������Ϣ
        if (ps == null||ps.length() < 0){
            ps="4";  //���û���趨��Ĭ����ʾ4�����
        }
        List<Information> information = informationService.getInformationByKey(key, (Integer.valueOf(pn) - 1) * Integer.valueOf(ps), Integer.valueOf(ps));
        return information;
    }

    @RequestMapping(value = "getInformationContentById",method = RequestMethod.GET)
    @ResponseBody
    public Information getInformationContextById(@RequestParam(value="id",required=false) Integer id) {
        Information information = informationService.getInformationContentById(id);
        return information;
    }

    @RequestMapping(value="getHotInformation",method=RequestMethod.GET)
    @ResponseBody
    public  List<Information> getHotInformation(HttpServletRequest request){
        String pn = request.getParameter("pageNo");
        if(pn == null || pn.length() < 0){
            pn = "0";
        }
        String ps = request.getParameter("pageSize");
        if (ps == null|| ps.length() < 0){
            ps = "4";
        }
        List<Information> information = informationService.getHotInformation(Integer.valueOf(pn) * Integer.valueOf(ps), Integer.valueOf(ps));
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
