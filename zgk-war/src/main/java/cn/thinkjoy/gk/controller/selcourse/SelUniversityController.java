package cn.thinkjoy.gk.controller.selcourse;

import cn.thinkjoy.gk.pojo.EduLevelNumberPojo;
import cn.thinkjoy.gk.pojo.SelUniversityPojo;
import cn.thinkjoy.gk.service.IDataDictService;
import cn.thinkjoy.gk.service.selcourse.ISelUniversityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 16/9/21.
 */
@Service
@RequestMapping("selUniversityController")
public class SelUniversityController {

    @Autowired
    private ISelUniversityService iSelUniversityService;

    @Autowired
    private IDataDictService iDataDictService;

    @RequestMapping("getUniversityInfoById")
    @ResponseBody
    private Map<String,Object> getUniversityInfoById(@RequestParam("universityId")String universityId){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("universityId",universityId);
        SelUniversityPojo selUniversityPojo=iSelUniversityService.selectUniversityById(map);
        List<EduLevelNumberPojo> eduLevelNumberPojoList=iSelUniversityService.selectEduLevelByUniversityId(map);
        selUniversityPojo.setEduLevelMajorNumber(eduLevelNumberPojoList);
        Integer openMajorNumber=0;
        for(EduLevelNumberPojo eduLevelNumberPojo:eduLevelNumberPojoList){
            openMajorNumber=openMajorNumber+Integer.valueOf(eduLevelNumberPojo.getNumber());
            selUniversityPojo.setOpenMajorNumber(openMajorNumber.toString());
        }

        Map<String, Object> propertyMap = new HashMap();
        if (StringUtils.isNotEmpty(selUniversityPojo.getProperty())) {
            Map<String,Object> map1=new HashMap<>();
            map1.put("type","FEATURE");
            List<Map<String,Object>> dictMapList=iDataDictService.queryDictList(map1);
            String[] propertys = selUniversityPojo.getProperty().split(",");
            for(String property:propertys){
                for(Map<String,Object> propertyDict:dictMapList){
                    if (property.equals(propertyDict.get("name"))){
                        propertyMap.put(propertyDict.get("id").toString(),property);
                    }
                }
            }
        }
//        map.put("propertys", propertyMap);
        selUniversityPojo.setPropertys(propertyMap);
        returnMap.put("universityInfo", selUniversityPojo);
        return returnMap;
    }

    @RequestMapping("getMajorList")
    @ResponseBody
    public Map<String,Object> getMajorList(@RequestParam("universityId")String universityId,
                                           @RequestParam(value="offset",required = false,defaultValue = "0")String offset,
                                           @RequestParam(value="rows",required = false,defaultValue = "10")String rows){
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("universityId",universityId);
        map.put("offset",offset);
        map.put("rows",rows);
        returnMap.put("majorList",iSelUniversityService.selectMajorList(map));
        return returnMap;
    }

}
