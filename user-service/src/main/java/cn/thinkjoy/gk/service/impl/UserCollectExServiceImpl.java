package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IUserCollectExDAO;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.pojo.VideoCoursePojo;
import cn.thinkjoy.gk.service.IUserCollectExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
@Service("UserCollectExServiceImpl")
public class UserCollectExServiceImpl implements IUserCollectExService {

    @Autowired
    private IUserCollectExDAO userCollectExDAO;

    /** 查询指定用户的收藏的所有学校 */
    public List<UserCollectPojo> getUserCollectUniversityPojoList(Map param){
        return userCollectExDAO.getUserCollectUniversityPojoList(param);
    }

    public int getUserCollectUniversityPojoCount(Map param) {
        return userCollectExDAO.getUserCollectUniversityPojoCount(param);
    }

    public int isUniversityCollect(Map param){
        return userCollectExDAO.isUniversityCollect(param);
    }

    /** 查询指定用户收藏的所有课程 */
    public List<VideoCoursePojo> getUserCollectVideoCoursePojoList(Map param){
        return userCollectExDAO.getUserCollectVideoCoursePojoList(param);
    }
}
