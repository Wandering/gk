package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.gk.common.ErrorCode;
import cn.thinkjoy.gk.common.ExceptionUtil;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.ExpertCustomerApeskDTO;
import cn.thinkjoy.gk.pojo.ExpertCustomerDTO;
import cn.thinkjoy.gk.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/17.
 * 对于专家来说,学生是客户
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert/admin/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    /**
     * 查询用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryCustomerInfo",method = RequestMethod.GET)
    public ExpertCustomerDTO queryCustomerInfo(@RequestParam Long orderId){
        if (orderId==null){
            ExceptionUtil.throwException(ErrorCode.PARAM_NULL);
        }
        //获取用户基本信息
        ExpertCustomerDTO expertCustomerDTO = customerService.queryBaseInfo(orderId);

        return expertCustomerDTO;
    }

    /**
     * 查询用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryCustomerApesk",method = RequestMethod.GET)
    public List<ExpertCustomerApeskDTO> queryCustomerApesk(@RequestParam Long orderId){
        if (orderId==null){
            ExceptionUtil.throwException(ErrorCode.PARAM_NULL);
        }

        List<ExpertCustomerApeskDTO> expertCustomerDTO = customerService.queryCustomerApesk(orderId);

        return expertCustomerDTO;
    }
}
