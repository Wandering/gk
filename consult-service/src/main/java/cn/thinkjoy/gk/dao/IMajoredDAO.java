package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.MajorDetailPojo;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
public interface IMajoredDAO {

    List<Map<String,Object>> getMajoreByParentId(@Param("parentId")Long i);

    List<SubjectDto> searchMajored(@Param("condition")MajoredQuery query);

    Integer searchMajoredCount(@Param("condition")MajoredQuery query);

    MajoredDto getMajoredById(@Param("id")String majoredId);

    List<Map<String,Object>> getUniversityByCode(@Param("code")String majoredCode, @Param("batchName")String name);

    List<MajorDetailPojo> getMajorDetailList(Map<String,Object> params);

    /**
     * 根据关键词查询专业基本信息
     *
     * @param keywords
     * @return
     */
    List<MajorDetailPojo> getMajoredInfoByKeywords(@Param("keywords") String keywords);

    List<Map<String,Object>> getMajorOpenUniversityList(@Param("condition")Map<String,Object> params);

    int getMajorOpenUniversityCount(@Param("condition")Map<String,Object> params);

    String getMajorEmploymentRate(@Param("condition")Map<String,Object> params);

    String getMajorIntroduce(@Param("condition")Map<String,Object> params);

    String getMajorSpecialisation(@Param("condition")Map<String,Object> params);
}
