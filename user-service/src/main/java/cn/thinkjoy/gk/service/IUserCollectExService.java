package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.UserCollectPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
public interface IUserCollectExService {

    /** 查询指定用户的收藏的所有学校 */
    List<UserCollectPojo> getUserCollectPojoList(Map param);

    /** 判断指定用户是否收藏指定学校，0为否，1为是 */
    int isUniversityCollect(Map param);

}