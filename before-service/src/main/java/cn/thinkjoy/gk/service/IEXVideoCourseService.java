package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.VideoCoursePojo;

import java.util.List;
import java.util.Map;

/**
 * Created by yhwang on 15/9/23.
 */
public interface IEXVideoCourseService {
    /**
     * 根据类别和科目查询课程
     * @return
     */
    List<VideoCoursePojo> getVideoListByParams(Map<String,Object> queryMap);

}
