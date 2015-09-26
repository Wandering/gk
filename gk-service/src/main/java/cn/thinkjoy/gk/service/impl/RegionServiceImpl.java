package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.domain.City;
import cn.thinkjoy.gk.domain.County;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.pojo.CityPojo;
import cn.thinkjoy.gk.pojo.CountyPojo;
import cn.thinkjoy.gk.pojo.ProvincePojo;
import cn.thinkjoy.gk.service.ICityService;
import cn.thinkjoy.gk.service.ICountyService;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkjoy on 15/9/25.
 */
@Service("RegionServiceImpl")
public class RegionServiceImpl implements IRegionService {
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private ICountyService countyService;


    @Override
    public List<ProvincePojo> getAllRegion() {
        List<ProvincePojo> provincePojos=new ArrayList<>();
        List<Province> provinces=provinceService.findAll();
        List<City> cities=cityService.findAll();
        Map<Long,List<CityPojo>> cityPojoMap=new HashMap<>();
        for(City city:cities){
            CityPojo cityPojo=new CityPojo();
            cityPojo.setId(city.getId());
            cityPojo.setName(city.getName());
            cityPojo.setProvinceId(city.getProvinceId());
            if(null==cityPojoMap.get(city.getProvinceId())){
                List<CityPojo> list=new ArrayList<>();
                list.add(cityPojo);
                cityPojoMap.put(city.getProvinceId(),list);
            }else{
                cityPojoMap.get(city.getProvinceId()).add(cityPojo);
            }
        }
        List<County> counties=countyService.findAll();
        Map<Long,List<CountyPojo>> countyPojoMap=new HashMap<>();
        for(County county:counties){
            CountyPojo countyPojo=new CountyPojo();
            countyPojo.setId(county.getId());
            countyPojo.setName(county.getName());
            countyPojo.setCityId(county.getCityId());
            if(null==countyPojoMap.get(county.getCityId())){
                List<CountyPojo> list=new ArrayList<>();
                list.add(countyPojo);
                countyPojoMap.put(county.getCityId(),list);
            }else{
                countyPojoMap.get(county.getCityId()).add(countyPojo);
            }
        }

        for(Province province:provinces){
            ProvincePojo provincePojo=new ProvincePojo();
            provincePojo.setId(province.getId());
            provincePojo.setName(province.getName());
            provincePojo.setCityList(cityPojoMap.get(province.getId()));
            provincePojos.add(provincePojo);
            for(CityPojo cityPojo:cityPojoMap.get(province.getId())){
                cityPojo.setCountyList(countyPojoMap.get(cityPojo.getId()));
            }
        }

        return provincePojos;
    }
}
