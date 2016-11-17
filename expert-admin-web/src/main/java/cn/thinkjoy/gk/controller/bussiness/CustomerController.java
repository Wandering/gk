package cn.thinkjoy.gk.controller.bussiness;

import cn.thinkjoy.gk.constant.SpringMVCConst;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yangyongping on 2016/11/17.
 * 对于专家来说,学生是客户
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping("/expert/admin/customer")
public class CustomerController {
    
}
