package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.University;
import cn.thinkjoy.gk.pojo.MajorDetailPojo;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.dto.MajoredCategoryRemoteDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/26.
 */
public interface IMajoredService {


    List<Map<String,Object>> getMajoreByParentId(Long i);

    List<SubjectDto> searchMajored(MajoredQuery query);

    Integer searchMajoredCount(MajoredQuery query);

    MajoredDto getMajoredById(String majoredId);

    List<Map<String,Object>> getUniversityByCode(String majoredCode, String name);

    List<MajorDetailPojo> getMajorDetailList(String code,String batch,Integer year,String subject);

    /**
     * 根据关键词查询专业基本信息
     *
     * @param keywords
     * @return
     */
    Map<String,String> getMajoredInfoByKeywords(String keywords);

    List<Map<String,Object>> getMajorOpenUniversityList(String majorId,int majorType,int offset,int rows);

    int getMajorOpenUniversityCount(String majorId,int majorType);

    Map<String,Object> getJobOrientation(int majorId);

    MajoredCategoryRemoteDTO getCategoryMajoredList(long categoryId);

    MajoredCategoryRemoteDTO getMajoredCategory(long type);

    List getMajoredByName(String majoredName,String type);

    Map getMajoredInfoById(String majorCode);

}
