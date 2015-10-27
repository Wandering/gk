package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.domain.VolunteerSchool;
import cn.thinkjoy.gk.service.IVolunteerSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zuohao on 15/10/26.
 */
@Controller
@RequestMapping(value = "guide/testController")
public class TestController {

    @Autowired
    private IVolunteerSchoolService volunteerSchoolService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam(value = "title")String title,@RequestParam(value="summary")String summary,@RequestParam(value="context")String context){

        System.out.println(context);
        VolunteerSchool volunteerSchool=new VolunteerSchool();
        volunteerSchool.setContent(context);
        volunteerSchool.setTitle(title);
        volunteerSchool.setSummary(summary);
        volunteerSchool.setAreaId(450000L);
        volunteerSchool.setHits(0);
        volunteerSchool.setCreateDate(0L);
        volunteerSchool.setCreator(0L);
        volunteerSchool.setCategoryId(2L);
        volunteerSchool.setStatus(1);
        volunteerSchoolService.add(volunteerSchool);
        return "success";
    }
}