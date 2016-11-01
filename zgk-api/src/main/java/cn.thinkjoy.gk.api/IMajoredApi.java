package cn.thinkjoy.gk.api;


import java.util.List;
import java.util.Map;

/**
 * Created by yangguorong on 16/11/1.
 *
 * 专业api
 */
public interface IMajoredApi {

    Object getMajoredCategory(long type);

    Object getCategoryMajoredList(long categoryId);

    List<Map<String,Object>> getMajorOpenUniversityList(String majorId, int majorType, int offset, int rows);

    int getMajorOpenUniversityCount(String majorId,int majorType);

    Map<String,Object> getJobOrientation(int majorId);

}
