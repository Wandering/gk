package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.gk.common.ExpertUserContext;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.ExpertOrderDTO;
import cn.thinkjoy.gk.service.IExpertOrderService;
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
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert/admin/order")
public class ExpertOrderController {
    @Autowired
    private IExpertOrderService expertOrderService;
    /**
     * 获取当前专家订单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryExpertOrder",method = RequestMethod.GET)
    public Object queryExpertOrder(@RequestParam(required = false,defaultValue = "1")Integer page,@RequestParam(required = false,defaultValue = "100")Integer rows) {
        Long expertId = ExpertUserContext.getCurrentUser().getId();
        BizData4Page<ExpertOrderDTO> bizData4Page = expertOrderService.queryOrder(expertId,page,rows);
        return bizData4Page;
    }
}
