package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.domain.GkinformationGkhot;
import cn.thinkjoy.gk.domain.PolicyInterpretation;
import cn.thinkjoy.gk.domain.VolunteerSchool;
import cn.thinkjoy.gk.service.IGkinformationGkhotService;
import cn.thinkjoy.gk.service.IPolicyInterpretationService;
import cn.thinkjoy.gk.service.IVolunteerSchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.util.Date;

/**
 * Created by zuohao on 15/10/26.
 */
@Controller
@RequestMapping(value = "guide/testController")
public class TestController {

    @Autowired
    private IGkinformationGkhotService gkinformationGkhotService;

    @Autowired
    private IPolicyInterpretationService policyInterpretationService;

    @Autowired
    private IVolunteerSchoolService volunteerSchoolService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam(value = "type")String type,@RequestParam(value = "batch",required = false)Long batch,@RequestParam(value = "title")String title,@RequestParam(value="summary")String summary,@RequestParam(value="context")String context){

        if(type.equals("gkhot")) {
            GkinformationGkhot gkinformationGkhot = new GkinformationGkhot();
            gkinformationGkhot.setStatus(0);
            gkinformationGkhot.setCreateDate(new Date().getTime());
            gkinformationGkhot.setLastModDate(new Date().getTime());
            gkinformationGkhot.setHotInformation(title);
            gkinformationGkhot.setInformationSubContent(summary);
            gkinformationGkhot.setInformationContent(context);
            gkinformationGkhot.setHotCount(0L);
            gkinformationGkhot.setAreaId(430000L);
            gkinformationGkhotService.saveGkinformationGkhot(gkinformationGkhot);
        }
        else if (type.equals("policy")){
            PolicyInterpretation policyInterpretation = new PolicyInterpretation();
            policyInterpretation.setStatus(1);
            policyInterpretation.setAdmissionBatchId(batch);
            policyInterpretation.setCreateDate(new Date().getTime());
            policyInterpretation.setLastModDate(new Date().getTime());
            policyInterpretation.setCategoryName(title);
            policyInterpretation.setContent(context);
            policyInterpretation.setAreaId(430000L);
            policyInterpretation.setProvinceId(1L);
            policyInterpretationService.add(policyInterpretation);
        }
        else if (type.equals("volunteer")){
            VolunteerSchool volunteerSchool=new VolunteerSchool();
            volunteerSchool.setStatus(0);
            volunteerSchool.setCategoryId(batch);
            volunteerSchool.setCreateDate(new Date().getTime());
            volunteerSchool.setLastModDate(new Date().getTime());
            volunteerSchool.setTitle(title);
            volunteerSchool.setSummary(summary);
            volunteerSchool.setContent(context);
            volunteerSchool.setAreaId(430000L);
            volunteerSchool.setHits(0);
            volunteerSchoolService.add(volunteerSchool);
        }
        else {
            return "fail";
        }
        return "success";

//        try {
//            request.setCharacterEncoding("UTF-8");
//
//            BufferedReader reader = request.getReader();
//            StringBuilder builder = new StringBuilder();
//
//            String s = null;
//            while ((s = reader.readLine()) != null) {
//                builder.append(s);
//            }
//            reader.close();
//
//
////            JSONObject payResult = JSON.parseObject(builder.toString());
////            System.out.println(builder.toString());
////            String ss=request.getParameter("context");
//            String[] ss=request.getParameterValues("title");
//            System.out.println();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
}