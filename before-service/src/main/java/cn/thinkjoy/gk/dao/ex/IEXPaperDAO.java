package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.gk.domain.ExaminationPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/24.
 */
public interface IEXPaperDAO {
    /**
     * 分页获取试卷
     * @param sortType
     * @param offSet
     * @param rows
     * @return
     */
    List<ExaminationPaper> getPaperPage(@Param("subjectId") Long subjectId,@Param("sortType")Integer sortType ,@Param("years")String years,@Param("searchName")String searchName,@Param("offSet") Integer offSet, @Param("rows")Integer rows,@Param("areaId")long areaId);
}
