package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUniversityDAO;
import cn.thinkjoy.gk.dao.IUniversityExDAO;
import cn.thinkjoy.gk.pojo.EnrollInfo;
import cn.thinkjoy.gk.pojo.MajoredScoreLinePojo;
import cn.thinkjoy.gk.pojo.PlanInfo;
import cn.thinkjoy.gk.pojo.UniversityDto;
import cn.thinkjoy.gk.service.IExUniversityService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkjoy on 15/9/26.
 */
@Service("ExUniversityServiceImpl")
public class ExUniversityServiceImpl implements IExUniversityService {
    @Autowired
    private IUniversityDAO iUniversityDAO;
    @Autowired
    private IUniversityExDAO iUniversityExDAO;

    @Override
    public List<Map> getProvinces() {
        return null;
    }

    @Override
    public List<Map> getUniversityType() {
        return null;
    }

    @Override
    public List<Map> getUniversiyBatch() {
        return null;
    }

    @Override
    public List<Map> getuniversityFeature() {
        return null;
    }

    @Override
    public List<UniversityDto> getUniversityList(Map<String, Object> universityQuery) {
        return iUniversityDAO.getUniversityList(universityQuery);
    }

    @Override
    public Integer getUniversityCount(Map<String, Object> universityQuery) {
        return iUniversityDAO.getUniversityCount(universityQuery);
    }


    @Override
    public UniversityDto getUniversityDetail(Map<String,Object> map) {
        return iUniversityDAO.getUniversityDetail(map);
    }

    @Override
    public List<EnrollInfo> getEnrollInfoByYear(Integer year,String universityId,long areaId) {
        Map<String, Object> params = new HashMap<>();

        params.put("year",year);
        params.put("universityId",universityId);
        params.put("areaId",areaId);

        return iUniversityDAO.getEnrollInfoByYear(params);
    }

    @Override
    public String getUniversityEnrollIntro(String id) {
        return iUniversityDAO.getUniversityEnrollIntro(id);
    }

    @Override
    public List<PlanInfo> getPlanInfosByYear(Integer year,String universityId,String batch,long areaId) {

        Map<String, Object> params = new HashMap<>();

        params.put("year",year);
        params.put("universityId",universityId);
        params.put("batch",batch);
        params.put("areaId",areaId);

        return iUniversityDAO.getPlanInfosByYear(params);
    }

    @Override
    public String getUniversityIntro(String schoolId) {
        return iUniversityDAO.getUniversityIntro(schoolId);
    }

    /**
     * 获取指定院校的专业录取分数线
     * @return
     */
    @Override
    public Map<String,Object> getMajoredScoreLinePojoList(long universityId,long areaId){
        Map<String,Object> param= Maps.newHashMap();
        param.put("universityId",universityId);
        param.put("areaId",areaId);
        List<String> years=getMajoredScoreLineYears(universityId,areaId);
        Map<String,Object> returnMap=Maps.newHashMap();
        for (String year:years){
            param.put("year",year);
            List<MajoredScoreLinePojo> majoredScoreLinePojoList=iUniversityExDAO.getMajoredScoreLinePojoList(param);
            returnMap.put(year+"年专业录取信息",majoredScoreLinePojoList);
        }
        return returnMap;
    }

    @Override
    public List<String> getMajoredScoreLineYears(long universityId,long areaId){
        Map<String,Object> param= Maps.newHashMap();
        param.put("universityId",universityId);
        param.put("areaId",areaId);
        return iUniversityExDAO.getMajoredScoreLineYears(param);
    }
}
