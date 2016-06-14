package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.domain.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/2/3.
 */
@Component
public class AreaMaps {
    private static Map<String, Long> areaMap = new HashMap<>();

    @Autowired
    private IProvinceService provinceService;

    @PostConstruct
    private Map<String, Long> getAreaMap()
    {
        if(areaMap.isEmpty())
        {
            initAreaInfo();
        }
        return areaMap;
    }


    @PostConstruct
    private void initAreaInfo()
    {
        List<Province> list =  provinceService.findAll();
        for (Province province:list) {
            areaMap.put(province.getCode(), Long.parseLong(String.valueOf(province.getId())));
        }
    }

    public Long getAreaId(Boolean boo){
        //默认为空
        try{
            return Long.valueOf(String.valueOf(getAreaMap().get(UserAreaContext.getCurrentUserArea())).toString());
        }catch (Exception e){
            return null;
        }
    }

    public Long getAreaId(){
        //当使用这一方法默认不使用分库
        return getAreaId(true);
    }
}
