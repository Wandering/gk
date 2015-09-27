package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.dto.MajoredDto;
import cn.thinkjoy.gk.dto.Subject;
import cn.thinkjoy.gk.query.MajoredQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/26.
 */
public interface IMajoredService {


    List<Map<String,Object>> getMajoreByParentId(int i);

    List<Subject> searchMajored(MajoredQuery query);

    Integer searchMajoredCount(MajoredQuery query);

    MajoredDto getMajoredByCode(String majoredCode);


    List<Map<String,Object>> getUniversityByCode(String majoredCode, String name);
}
