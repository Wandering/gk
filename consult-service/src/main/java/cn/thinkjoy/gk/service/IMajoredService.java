package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/26.
 */
public interface IMajoredService {


    List<Map<String,Object>> getMajoreByParentId(int i);

    List<SubjectDto> searchMajored(MajoredQuery query);

    Integer searchMajoredCount(MajoredQuery query);

    MajoredDto getMajoredByCode(String majoredCode);


    List<Map<String,Object>> getUniversityByCode(String majoredCode, String name);
}
