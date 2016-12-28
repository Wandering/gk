package cn.thinkjoy.gk.service.impl.api;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.gk.api.IUniversityApi;
import cn.thinkjoy.gk.dao.IUniversityExDAO;
import cn.thinkjoy.gk.dao.IUniversityInfoDao;
import cn.thinkjoy.gk.domain.GkAdmissionLine;
import cn.thinkjoy.gk.pojo.UniversityEnrollingDTO;
import cn.thinkjoy.gk.service.information.service.ex.IUniversityEnrollingExService;
import cn.thinkjoy.zgk.remote.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/10/31.
 */
@Service("universityApi")
public class UniversityApiImpl implements IUniversityApi {

    @Autowired
    private IUniversityInfoDao iUniversityInfoDao;
    @Autowired
    private IUniversityService universityService;
    @Autowired
    private IUniversityEnrollingExService universityEnrollingExService;
    @Autowired
    private IUniversityExDAO universityExDAO;


    @Override
    public List<Map<String, Object>> getBatchByYearAndArea(Map<String, Object> map) {
        return iUniversityInfoDao.getBatchByYearAndArea(map);
    }

    @Override
    public List getDataDictList(String type) {
        return universityService.getDataDictListByType(type);
    }

    @Override
    public List<String> getEnrollingYearsByProvinceId(long currentProId,long schoolProId) {
        return iUniversityInfoDao.getEnrollingYearsByProvinceId(currentProId,schoolProId);
    }

    @Override
    public List<Map<String, Object>> getMajorPlanConditions(Map<String, String> map) {
        return iUniversityInfoDao.getMajorPlanConditions(map);
    }

    @Override
    public BizData4Page<GkAdmissionLine> getAdmissionLineList(Map<String, Object> map, Integer page, Integer rows) {
        List<UniversityEnrollingDTO> list = universityEnrollingExService.queryPage(map,(page-1)*rows,rows);
        Integer count = universityEnrollingExService.count(map);
        BizData4Page<GkAdmissionLine> dataPage = new BizData4Page<>();
        dataPage.setRows(domain2GkAdmissionLine(list));
        dataPage.setRecords(count);
        return dataPage;
    }


    /**
     * api需要domainList和admindomainList转换
     * @param universityEnrollingDTOs
     * @return
     */
    private List<GkAdmissionLine> domain2GkAdmissionLine(List<UniversityEnrollingDTO> universityEnrollingDTOs){
        if(universityEnrollingDTOs==null)return null;
        List<GkAdmissionLine> gkAdmissionLines = new ArrayList<>();
        for(UniversityEnrollingDTO universityEnrollingDTO:universityEnrollingDTOs){
            gkAdmissionLines.add(domain2GkAdmissionLine(universityEnrollingDTO));
        }
        return gkAdmissionLines;
    }

    /**
     * api需要domain和admindomain转换
     * @param universityEnrollingDTO
     * @return
     */
    private GkAdmissionLine domain2GkAdmissionLine(UniversityEnrollingDTO universityEnrollingDTO){
        GkAdmissionLine gkAdmissionLine=new GkAdmissionLine();
        gkAdmissionLine.setId(universityEnrollingDTO.getUniversityId());
        gkAdmissionLine.setName(universityEnrollingDTO.getName());
        gkAdmissionLine.setAverageScore(universityEnrollingDTO.getAverageScore());
        gkAdmissionLine.setBatchname(universityEnrollingDTO.getBatchname());
        gkAdmissionLine.setHighestScore(universityEnrollingDTO.getHighestScore());
        gkAdmissionLine.setLowestScore(universityEnrollingDTO.getLowestScore());
        gkAdmissionLine.setProperty(universityEnrollingDTO.getProperty());
        gkAdmissionLine.setTypename(universityEnrollingDTO.getTypename());
        gkAdmissionLine.setYear(universityEnrollingDTO.getYear());
        gkAdmissionLine.setSubjection(universityEnrollingDTO.getSubjection());
        gkAdmissionLine.setEnrollingNumber(universityEnrollingDTO.getRealEnrollingNumber());
        return gkAdmissionLine;
    }

    @Override
    public List getUniversityMajorEnrollingPlanList(Map<String, Object> condition) {
        return universityExDAO.getUniversityMajorEnrollingPlanList(condition);
    }
}
