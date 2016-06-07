//package cn.thinkjoy.gk.controller;
//
//import cn.thinkjoy.gk.common.ZGKBaseController;
//import cn.thinkjoy.gk.domain.GkinformationGkhot;
//import cn.thinkjoy.gk.domain.PolicyInterpretation;
//import cn.thinkjoy.gk.domain.VolunteerSchool;
//import cn.thinkjoy.gk.service.IGkinformationGkhotService;
//import cn.thinkjoy.gk.service.IPolicyInterpretationService;
//import cn.thinkjoy.gk.service.IUniversityExService;
//import cn.thinkjoy.gk.service.IVolunteerSchoolService;
//import cn.thinkjoy.gk.util.ExcelUtil;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.google.common.collect.Maps;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.MultipartResolver;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zuohao on 15/10/26.
// */
//@Controller
//@RequestMapping(value = "guide/testController")
//public class TestController extends ZGKBaseController {
//
//    @Autowired
//    private IGkinformationGkhotService gkinformationGkhotService;
//
//    @Autowired
//    private IPolicyInterpretationService policyInterpretationService;
//
//    @Autowired
//    private IVolunteerSchoolService volunteerSchoolService;
//
//    @Autowired
//    private IUniversityExService iUniversityExService;
//
//    @RequestMapping(value = "/test")
//    @ResponseBody
//    public String test(@RequestParam(value = "type")String type,@RequestParam(value = "batch",required = false)Long batch,@RequestParam(value = "title")String title,@RequestParam(value="summary")String summary,@RequestParam(value="context")String context) throws Exception {
//        long areaId= getAreaId();
//        if(type.equals("gkhot")) {
//            GkinformationGkhot gkinformationGkhot = new GkinformationGkhot();
//            gkinformationGkhot.setStatus(0);
//            gkinformationGkhot.setCreateDate(new Date().getTime());
//            gkinformationGkhot.setLastModDate(new Date().getTime());
//            gkinformationGkhot.setHotInformation(title);
//            gkinformationGkhot.setInformationSubContent(summary);
//            gkinformationGkhot.setInformationContent(context);
//            gkinformationGkhot.setHotCount(0L);
//            gkinformationGkhot.setAreaId(areaId);
//            gkinformationGkhotService.saveGkinformationGkhot(gkinformationGkhot);
//        }
//        else if (type.equals("policy")){
//            PolicyInterpretation policyInterpretation = new PolicyInterpretation();
//            policyInterpretation.setStatus(1);
//            policyInterpretation.setAdmissionBatchId(batch);
//            policyInterpretation.setCreateDate(new Date().getTime());
//            policyInterpretation.setLastModDate(new Date().getTime());
//            policyInterpretation.setCategoryName(title);
//            policyInterpretation.setContent(context);
//            policyInterpretation.setAreaId(areaId);
//            policyInterpretation.setProvinceId(1L);
//            policyInterpretationService.add(policyInterpretation);
//        }
//        else if (type.equals("volunteer")){
//            VolunteerSchool volunteerSchool=new VolunteerSchool();
//            volunteerSchool.setStatus(0);
//            volunteerSchool.setCategoryId(batch);
//            volunteerSchool.setCreateDate(new Date().getTime());
//            volunteerSchool.setLastModDate(new Date().getTime());
//            volunteerSchool.setTitle(title);
//            volunteerSchool.setSummary(summary);
//            volunteerSchool.setContent(context);
//            volunteerSchool.setAreaId(areaId);
//            volunteerSchool.setHits(0);
//            volunteerSchoolService.add(volunteerSchool);
//        }
//        else {
//            return "fail";
//        }
//        return "success";
//
////        try {
////            request.setCharacterEncoding("UTF-8");
////
////            BufferedReader reader = request.getReader();
////            StringBuilder builder = new StringBuilder();
////
////            String s = null;
////            while ((s = reader.readLine()) != null) {
////                builder.append(s);
////            }
////            reader.close();
////
////
//////            JSONObject payResult = JSON.parseObject(builder.toString());
//////            System.out.println(builder.toString());
//////            String ss=request.getParameter("context");
////            String[] ss=request.getParameterValues("title");
////            System.out.println();
////        }catch(Exception e){
////            e.printStackTrace();
////        }
//    }
//
//    @RequestMapping(value = "uploadMajoredScoreLine")
//    @ResponseBody
//    public String uploadMajoredScoreLine(HttpServletRequest request) throws Exception {
//        List<Map<Integer, String>> data=getData(request, 8);
//        long areaId= getAreaId();
//        for (Map<Integer, String> pMap : data) {
//            String majoredName=pMap.get(0).trim();
//            String universityName=pMap.get(1).trim();
//            String averageScore=pMap.get(2).trim();
//            String highestScore=pMap.get(3).trim();
//            String provinceName=pMap.get(4).trim();
//            String subject=pMap.get(5).trim();
//            String year=pMap.get(6).trim();
//            String enrollBatch=pMap.get(7).trim();
//            Map<String,Object> map= Maps.newHashMap();
////            map.put("universityId",schoolMap.get(universityName));
//            map.put("universityName",universityName);
////            map.put("majoredId",majoredId);
//            map.put("majoredName",majoredName);
//            map.put("provinceName",provinceName);
//            map.put("year",year);
//            map.put("enrollBatch",enrollBatch);
//            map.put("subject",subject);
//            map.put("highestScore",highestScore);
//            map.put("averageScore",averageScore);
//            map.put("areaId",areaId);
//            iUniversityExService.saveMajoredScoreLine(map);
//        }
//        return "success";
//    }
//
//    public List<Map<Integer, String>> getData(HttpServletRequest request,int cellNum){
//        MultipartFile file = getUploadFile(request);
//        if (null == file) {
////            BusinessErrors.ex("上传文件无法识别");
//        }
//        List<Map<Integer, String>> data = null;
//        try {
//            data = ExcelUtil.poiRead(file, cellNum);
//        } catch (Exception e) {
////            BusinessErrors.ex("上传失败,请确认您上传的文件是否符合要求!");
//        }
//        if (null == data || data.isEmpty()) {
////            BusinessErrors.ex("上传失败,请确认您上传的文件是否符合要求!");
//        }
//        return data;
//    }
//    private MultipartFile getUploadFile(HttpServletRequest request) {
//        // 设置上下方文
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//                request.getSession().getServletContext());
//        // 检查form是否有enctype="multipart/form-data"
//        if (!multipartResolver.isMultipart(request)) {
//            return null;
//        }
////        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
//        Iterator<String> iter = multiRequest.getFileNames();
//        while (iter.hasNext()) {
//            // 由CommonsMultipartFile继承而来,拥有上面的方法.
//            MultipartFile file = multiRequest.getFile(iter.next());
//            if (file != null) {
//                return file;
//            }
//        }
//        return null;
//    }
//
//
//    @RequestMapping(value = "uploadFile")
//    @ResponseBody
//    public String uploadFile() {
//        String url = "http://cs-dev.thinkjoy.com.cn/rest/v1/uploadFile";
////        FileSystemResource resource = new FileSystemResource(new File("/Users/zuohao/Desktop/test4.html"));
//        FileSystemResource resource = new FileSystemResource(new File("/Users/zuohao/Documents/转码视频/化学/14484335628433uvdfye8ppaks82haldw.mp4"));
//        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
//        RestTemplate template = new RestTemplate();//这里大家可以用其他的httpClient均可以
//        param.add("file", resource);
//        param.add("productCode", "zgk");
//        param.add("bizSystem", "zgk");
//        param.add("spaceName", "zgk");
//        param.add("userId", "zgk");
//        param.add("dirId", "0");
//        template.getMessageConverters().add(new FastJsonHttpMessageConverter());
//        long start=System.currentTimeMillis();
//        System.out.println("开始时间:"+start);
//        String st = template.postForObject(url, param, String.class);
//        long end=System.currentTimeMillis();
//        System.out.println("结束时间:"+end);
//        System.out.println("上传耗费时间："+(end-start)+"ms");
//        return st+"上传耗费时间："+(end-start)+"ms";
//    }
//}