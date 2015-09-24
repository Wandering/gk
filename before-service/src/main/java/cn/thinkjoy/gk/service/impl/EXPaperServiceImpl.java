package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IEXPaperDAO;
import cn.thinkjoy.gk.domain.ExaminationPaper;
import cn.thinkjoy.gk.service.IEXPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhwang on 15/9/24.
 */
@Service("IEXPaperServiceImpl")
public class EXPaperServiceImpl implements IEXPaperService{
    @Autowired
    private IEXPaperDAO paperDAO;
    /**
     * 分页获取试卷
     *
     * @param sortType
     * @param offSet
     * @param pageSize
     * @return
     */
    @Override
    public List<ExaminationPaper> getPaperPage(Integer sortType,String years, Integer offSet, Integer pageSize) {

        return paperDAO.getPaperPage(sortType,years,offSet,pageSize);
    }
}
