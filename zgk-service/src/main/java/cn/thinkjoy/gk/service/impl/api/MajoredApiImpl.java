package cn.thinkjoy.gk.service.impl.api;

import cn.thinkjoy.gk.api.IMajoredApi;
import cn.thinkjoy.gk.dao.selcourse.ISelClassesDao;
import cn.thinkjoy.gk.pojo.Bases;
import cn.thinkjoy.gk.service.IMajoredService;
import cn.thinkjoy.gk.service.selcourse.ISelClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by yangguorong on 16/11/1.
 */
@Service("majoredApi")
public class MajoredApiImpl implements IMajoredApi {

    @Autowired
    private IMajoredService majoredService;

    @Autowired
    private ISelClassesService selClassesService;

    @Autowired
    private ISelClassesDao selClassesDao;

    @Override
    public Object getMajoredCategory(long type) {
        return majoredService.getMajoredCategory(type);
    }

    @Override
    public Object getCategoryMajoredList(String categoryId) {
        return majoredService.getCategoryMajoredList(categoryId);
    }

    @Override
    public List<Map<String, Object>> getMajorOpenUniversityList(String majorId, int majorType, int offset, int rows) {
        return majoredService.getMajorOpenUniversityList(majorId,majorType,offset,rows);
    }

    @Override
    public int getMajorOpenUniversityCount(String majorId, int majorType) {
        return majoredService.getMajorOpenUniversityCount(majorId,majorType);
    }

    @Override
    public Map<String, Object> getJobOrientation(String majorId) {
        return majoredService.getJobOrientation(majorId);
    }

    @Override
    public Map getMajoredInfoByCode(String majorCode) {
        return majoredService.getMajoredInfoById(majorCode);
    }

    @Override
    public List getMajoredByName(String majoredName, String type) {
        return majoredService.getMajoredByName(majoredName,type);
    }

    @Override
    public Object selectMajorTopCount(int count,String year) {
        return selClassesService.selectMajorTop3(count,year);
    }

    @Override
    public List<String> getEnrollingYear() {
        return selClassesDao.getEnrollingYear();
    }
}
