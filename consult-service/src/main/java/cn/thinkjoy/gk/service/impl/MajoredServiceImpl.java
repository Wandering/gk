package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.dao.IMajoredDAO;
import cn.thinkjoy.gk.dto.*;
import cn.thinkjoy.gk.pojo.MajorDetailPojo;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.service.IMajoredService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.zgk.common.MD5Util;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
@Service("MajoredServiceImpl")
public class MajoredServiceImpl implements IMajoredService {

    @Autowired
    private IMajoredDAO majoredDAO;

    @Override
    public List<Map<String, Object>> getMajoreByParentId(Long i) {
        return majoredDAO.getMajoreByParentId(i);
    }

    @Override
    public List<SubjectDto> searchMajored(MajoredQuery query) {
        return majoredDAO.searchMajored(query);
    }

    @Override
    public Integer searchMajoredCount(MajoredQuery query) {
        return majoredDAO.searchMajoredCount(query);
    }

    @Override
    public MajoredDto getMajoredById(String majoredCode) {
        return majoredDAO.getMajoredById(majoredCode);
    }

    @Override
    public List<Map<String, Object>> getUniversityByCode(String majoredCode, String name) {
        return majoredDAO.getUniversityByCode(majoredCode,name);
    }

    @Override
    public List<MajorDetailPojo> getMajorDetailList(String code, String batch, Integer year,String subject) {
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        params.put("batch",batch);
        params.put("year",year);
        params.put("subject",subject);
        return majoredDAO.getMajorDetailList(params);
    }

    @Override
    public Map<String, String> getMajoredInfoByKeywords(String keywords) {

        Map<String,String> map = Maps.newHashMap();

        String key = "majored" + MD5Util.md5String(keywords);
        RedisRepository redisRepository = RedisUtil.getInstance();
        boolean flag = redisRepository.exists(key);
        if (flag) {
            map = JSON.parseObject(redisRepository.get(key).toString(), Map.class);
        } else {

            List<MajorDetailPojo> pojos = majoredDAO.getMajoredInfoByKeywords(keywords);

            for(MajorDetailPojo pojo : pojos){
                map.put(pojo.getId().toString(),pojo.getName());
            }

            redisRepository.set(key,JSON.toJSON(map));
        }

        return map;
    }

    @Override
    public List<Map<String, Object>> getMajorOpenUniversityList(String majorId, int majorType, int offset, int rows) {
        Map<String,Object> params = new HashMap<>();
        params.put("majorId",majorId);
        params.put("educationLevel",majorType);
        params.put("offset",offset);
        params.put("rows",rows);
        return majoredDAO.getMajorOpenUniversityList(params);
    }

    @Override
    public int getMajorOpenUniversityCount(String majorId, int majorType) {
        Map<String,Object> params = new HashMap<>();
        params.put("majorId",majorId);
        params.put("educationLevel",majorType);
        return majoredDAO.getMajorOpenUniversityCount(params);
    }

    @Override
    public Map<String, Object> getJobOrientation(int majorId) {
        Map<String,Object> params = new HashMap<>();
        params.put("majorId",majorId);
        String specialisation=majoredDAO.getMajorSpecialisation(params);
        String employmentRate=majoredDAO.getMajorEmploymentRate(params);
        params.put("introduce",specialisation);
        params.put("employmentRate",employmentRate);
        return params;
    }

    @Override
    public MajoredCategoryRemoteDTO getCategoryMajoredList(long categoryId) {

//        Map<String,Object> map= Maps.newHashMap();
//        map.put("categoryId",categoryId);
        List<CategoryMajoredDTO> categoryMajoredDTOList = majoredDAO.getCategoryMajoredList(categoryId);

        MajoredCategoryRemoteDTO majoredCategoryRemoteDTO=new MajoredCategoryRemoteDTO();//大门类
        majoredCategoryRemoteDTO.setName(categoryMajoredDTOList.get(0).getDisciplineCategoriesName());
        majoredCategoryRemoteDTO.setId(categoryId);
        List<String> mcList= Lists.newArrayList();
        for (CategoryMajoredDTO categoryMajoredDTO:categoryMajoredDTOList){
            //判断专业门类是否已存在
            if (mcList.contains(categoryMajoredDTO.getMajorCategoryName())){//如果有
                for (MajoredCategoryRemoteDTO majoredCategoryRemoteDTO1:majoredCategoryRemoteDTO.getChildList()){
                    if (majoredCategoryRemoteDTO1.getName().equals(categoryMajoredDTO.getMajorCategoryName())){
                        majoredCategoryRemoteDTO1.setMajoredNumber(majoredCategoryRemoteDTO1.getMajoredNumber()+1);

                        MajoredCategoryRemoteDTO majoredCategoryRemoteDTO2=new MajoredCategoryRemoteDTO();
                        majoredCategoryRemoteDTO2.setId(categoryMajoredDTO.getMajoredId());
                        majoredCategoryRemoteDTO2.setName(categoryMajoredDTO.getMajoredName());
                        majoredCategoryRemoteDTO1.getChildList().add(majoredCategoryRemoteDTO2);
                        break;
                    }
                }
            }else {
                mcList.add(categoryMajoredDTO.getMajorCategoryName());//记录专业门类
                MajoredCategoryRemoteDTO majoredCategoryRemoteDTO1=new MajoredCategoryRemoteDTO();//专业门类
                majoredCategoryRemoteDTO1.setId(categoryMajoredDTO.getMajorCategoryId());//专业门类Id
                majoredCategoryRemoteDTO1.setName(categoryMajoredDTO.getMajorCategoryName());//专业门类Name
                majoredCategoryRemoteDTO1.setMajoredNumber(1);

                MajoredCategoryRemoteDTO majoredCategoryRemoteDTO2=new MajoredCategoryRemoteDTO();
                majoredCategoryRemoteDTO2.setId(categoryMajoredDTO.getMajoredId());
                majoredCategoryRemoteDTO2.setName(categoryMajoredDTO.getMajoredName());
                majoredCategoryRemoteDTO1.getChildList().add(majoredCategoryRemoteDTO2);
                majoredCategoryRemoteDTO.getChildList().add(majoredCategoryRemoteDTO1);
            }
        }
        return majoredCategoryRemoteDTO;

//        return majoredDAO.getCategoryMajoredList(categoryId);
    }

    @Override
    public MajoredCategoryRemoteDTO getMajoredCategory(long type) {
        Map<String,Object> map= Maps.newHashMap();
        map.put("type",type);
        List<MajoredCategoryRemoteDTO> majoredCategoryRemoteDTOList = majoredDAO.getMajoredCategory(type);
        MajoredCategoryRemoteDTO majoredCategoryRemoteDTO1=new MajoredCategoryRemoteDTO();
        majoredCategoryRemoteDTO1.setId(type);
        if (type==1){
            majoredCategoryRemoteDTO1.setName("本科");
        }else {
            majoredCategoryRemoteDTO1.setName("专科");
        }
        majoredCategoryRemoteDTO1.setChildNumber(0);
        for (MajoredCategoryRemoteDTO majoredCategoryRemoteDTO:majoredCategoryRemoteDTOList){
            majoredCategoryRemoteDTO1.getChildList().add(majoredCategoryRemoteDTO);
            majoredCategoryRemoteDTO1.setChildNumber(majoredCategoryRemoteDTO1.getChildNumber()+1);
            majoredCategoryRemoteDTO.setChildList(null);
        }
        return majoredCategoryRemoteDTO1;
    }

    @Override
    public List getMajoredByName(String majoredName, String type) {

        Map<String,Object> map= Maps.newHashMap();
        map.put("majoredName",majoredName);
        map.put("type",type);

        List<MajoredQueryDTO> majoredQueryDTOList=majoredDAO.getMajoredByName(map);
        List<Map<String,Object>> mapList= Lists.newArrayList();
        List<String> mList=Lists.newArrayList();
        for (MajoredQueryDTO majoredQueryDTO:majoredQueryDTOList){
            if(mList.contains(majoredQueryDTO.getDisciplineCategoriesName()+majoredQueryDTO.getMajorCategoryName())){//如果存在
                for (Map<String,Object> map1:mapList){
                    if (map1.get("disciplineCategoriesName").equals(majoredQueryDTO.getDisciplineCategoriesName())
                            &&map1.get("majorCategoryName").equals(majoredQueryDTO.getMajorCategoryName())){
                        Map<String,Object> map2=Maps.newHashMap();
                        map2.put("majoredId",majoredQueryDTO.getMajoredId());
                        map2.put("majoredName",majoredQueryDTO.getMajoredName());
                        List ll= (List) map1.get("majoredList");
                        ll.add(map2);
                        break;
                    }
                }
            }else {
                mList.add(majoredQueryDTO.getDisciplineCategoriesName() + majoredQueryDTO.getMajorCategoryName());
                Map<String,Object> map1=Maps.newHashMap();
                map1.put("disciplineCategoriesName",majoredQueryDTO.getDisciplineCategoriesName());
                map1.put("majorCategoryName",majoredQueryDTO.getMajorCategoryName());
                Map<String,Object> map2=Maps.newHashMap();
                map2.put("majoredId",majoredQueryDTO.getMajoredId());
                map2.put("majoredName",majoredQueryDTO.getMajoredName());
                List ll=Lists.newArrayList();
                ll.add(map2);
                map1.put("majoredList",ll);
                mapList.add(map1);
            }
        }
        return mapList;

//        Map<String,Object> map= Maps.newHashMap();
//        map.put("majoredName",majoredName);
//        map.put("type",type);
//        return majoredDAO.getMajoredByName(map);
    }

    @Override
    public Map getMajoredInfoById(String majorCode) {
        MajorInfoDTO majorInfoDTO=majoredDAO.getMajoredInfoById(majorCode);
        Map<String,Object> map= Maps.newHashMap();
        if (majorInfoDTO!=null) {
            map.put("id", majorCode);
            map.put("majorName", majorInfoDTO.getMajorName());
            map.put("majorCode", majorInfoDTO.getMajorCode());
            map.put("degreeOffered", majorInfoDTO.getDegreeOffered());
            map.put("schoolingDuration", majorInfoDTO.getSchoolingDuration());
            map.put("offerCourses", majorInfoDTO.getOfferCourses());
            map.put("majorIntroduce", majorInfoDTO.getMajorIntroduce());
            map.put("employmentRate", majorInfoDTO.getEmploymentRate());
            map.put("salary", majorInfoDTO.getSalary());
            map.put("salaryRank", majorInfoDTO.getSalaryRank());
            map.put("fmRatio", majorInfoDTO.getFmRatio());
        }
        return map;
    }

    public List<UniversityDTO> getMajorOpenUniversityList(Map<String, Object> map,int offset,int rows,String orderBy,String sortBy) {
        return getMajorOpenUniversityList(map,offset,rows,orderBy,sortBy);
    }
}
