package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.gk.pojo.VideoCoursePojo;

import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/23.
 */
public interface IEXVideoCourseDAO {
    /**
     * 根据分类和科目查询课程
     * @return
     */
    List<VideoCoursePojo> getVideoListByParams(Map<String,Object> queryMap);
}
