package cn.thinkjoy.gk.dao;

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

    List<Map<String,Object>> getMajoreByParentId(@Param("parentId")int i);

    List<SubjectDto> searchMajored(@Param("condition")MajoredQuery query);

    Integer searchMajoredCount(@Param("condition")MajoredQuery query);

    MajoredDto getMajoredByCode(@Param("code")String majoredCode);

    List<Map<String,Object>> getUniversityByCode(@Param("code")String majoredCode, @Param("batchName")String name);
}
