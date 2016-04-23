package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
public interface IUserCollectExDAO {

    /** 查询指定用户的收藏的所有学校 */
    List<UserCollectPojo> getUserCollectUniversityPojoList(Map param);

    /** 查询指定用户的收藏的所有学校总数 */
    int getUserCollectCount(Map param);

    /** 判断指定用户是否收藏指定项目，0为否，1为是 */
    int isCollect(Map param);

    List<VideoCoursePojo> getUserCollectVideoCoursePojoList(Map param);
}
