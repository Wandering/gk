package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zuohao on 16/9/19.
 * 按课程查询专业
 */
@Controller
@RequestMapping("selClassesController")
public class SelClassesController {

    @Autowired
    private ISelClassesService iSelClassesService;

    @RequestMapping
    @ResponseBody
    public void getSchoolAndMajorNumber(){

    }
}
