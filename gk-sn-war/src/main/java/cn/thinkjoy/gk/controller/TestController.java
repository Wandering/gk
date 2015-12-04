package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.domain.GkinformationGkhot;
import cn.thinkjoy.gk.service.IGkinformationGkhotService;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Date;

/**
 * Created by zuohao on 15/10/26.
 */
@Controller
@RequestMapping(value = "guide/testController")
public class TestController {

    @Autowired
    private IGkinformationGkhotService gkinformationGkhotService;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(@RequestParam(value = "title") String title, @RequestParam(value = "summary") String summary, @RequestParam(value = "context") String context) {

        System.out.println(context);
        GkinformationGkhot gkinformationGkhot = new GkinformationGkhot();
        gkinformationGkhot.setStatus(0);
        gkinformationGkhot.setCreateDate(new Date().getTime());
        gkinformationGkhot.setLastModDate(new Date().getTime());
        gkinformationGkhot.setHotInformation(title);
        gkinformationGkhot.setInformationSubContent(summary);
        gkinformationGkhot.setInformationContent(context);
        gkinformationGkhot.setHotCount(0L);
        gkinformationGkhot.setAreaId(610000L);
        gkinformationGkhotService.saveGkinformationGkhot(gkinformationGkhot);
        return "success";
    }

    public void tt(){
        String url = "http://cs-dev.thinkjoy.cn/rest/v1/uploadFile";
        FileSystemResource resource = new FileSystemResource(new File("文件路径"));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        RestTemplate template = new RestTemplate();//这里大家可以用其他的httpClient均可以

        param.add("file", resource);
        template.getMessageConverters().add(new FastJsonHttpMessageConverter());
        String st = template.postForObject(url, param, String.class);
    }

}