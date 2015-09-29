package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExaminationPaper;
import java.util.List;

/**
 * Created by yhwang on 15/9/24.
 */
public interface IEXPaperService {
    /**
     * 分页获取试卷
     * @param sortType
     * @param offSet
     * @param pageSize
     * @return
     */
    List<ExaminationPaper> getPaperPage(Long subjectId,Integer sortType ,String years,String searchName, Integer offSet, Integer pageSize);
}
