package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUserCollectDAO;
import cn.thinkjoy.gk.dao.ex.IAppointmentExDAO;
import cn.thinkjoy.gk.dao.ex.IUserCollectExDAO;
import cn.thinkjoy.gk.domain.UserCollect;
import cn.thinkjoy.gk.pojo.UserCollectPojo;
import cn.thinkjoy.gk.service.IUserCollectExService;
import cn.thinkjoy.gk.service.IUserCollectService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public List<UserCollectPojo> getUserCollectPojoList(Map param){
        return userCollectExDAO.getUserCollectPojoList(param);
    }

    public int isUniversityCollect(Map param){
        return userCollectExDAO.isUniversityCollect(param);
    }
}
