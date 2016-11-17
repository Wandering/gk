package cn.thinkjoy.gk.service.impl.api;

import cn.thinkjoy.gk.api.IMajoredApi;
import cn.thinkjoy.gk.service.IMajoredService;
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

    @Override
    public Object getMajoredCategory(long type) {
        return majoredService.getMajoredCategory(type);
    }

    @Override
    public Object getCategoryMajoredList(long categoryId) {
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
    public Map<String, Object> getJobOrientation(int majorId) {
        return majoredService.getJobOrientation(majorId);
    }

    @Override
    public Map getMajoredInfoByCode(String majorCode) {
        return majoredService.getMajoredInfoById(majorCode);
    }
}
