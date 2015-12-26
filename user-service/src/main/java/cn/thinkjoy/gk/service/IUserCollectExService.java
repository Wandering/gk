package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.VideoCourse;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
public interface IUserCollectExService {

    /** 查询指定用户的收藏的所有学校 */
    List<UserCollectPojo> getUserCollectUniversityPojoList(Map param);

    /** 查询指定用户指定项目的收藏的总数 */
    int getUserCollectCount(Map param);

    /** 判断指定用户是否收藏指定项目，0为否，1为是 */
    int isCollect(Map param);

    /** 查询指定用户收藏的所有课程 */
    List<VideoCoursePojo> getUserCollectVideoCoursePojoList(Map param);

}
