package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yhwang on 15/9/23.
 */
public interface IEXVideoCourseDAO {
    /**
     * 根据分类和科目查询课程
     * @return
     */
    List<VideoCoursePojo> getVideoListByParams(@Param("subjectId")Long subjectId,@Param("classifyType")Integer classifyType,@Param("sortType") Integer sortType,@Param("offset")Integer offset,@Param("rows")Integer rows);

}
