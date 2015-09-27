package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IMajoredDAO;
import cn.thinkjoy.gk.pojo.MajoredDto;
import cn.thinkjoy.gk.pojo.SubjectDto;
import cn.thinkjoy.gk.query.MajoredQuery;
import cn.thinkjoy.gk.service.IMajoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
@Service("MajoredServiceImpl")
public class MajoredServiceImpl implements IMajoredService {

    @Autowired
    private IMajoredDAO majoredDAO;

    @Override
    public List<Map<String, Object>> getMajoreByParentId(int i) {
        return majoredDAO.getMajoreByParentId(i);
    }

    @Override
    public List<SubjectDto> searchMajored(MajoredQuery query) {
        return majoredDAO.searchMajored(query);
    }

    @Override
    public Integer searchMajoredCount(MajoredQuery query) {
        return majoredDAO.searchMajoredCount(query);
    }

    @Override
    public MajoredDto getMajoredByCode(String majoredCode) {
        return majoredDAO.getMajoredByCode(majoredCode);
    }

    @Override
    public List<Map<String, Object>> getUniversityByCode(String majoredCode, String name) {
        return majoredDAO.getUniversityByCode(majoredCode,name);
    }

}
