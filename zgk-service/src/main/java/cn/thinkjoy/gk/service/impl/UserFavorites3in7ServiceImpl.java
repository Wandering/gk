package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.util.UserContext;
import cn.thinkjoy.gk.dao.IUserFavorites3in7DAO;
import cn.thinkjoy.gk.service.IUserFavorites3in7Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/16.
 */
@Service("UserFavorites3in7ServiceImpl")
public class UserFavorites3in7ServiceImpl implements IUserFavorites3in7Service {
    @Autowired
    IUserFavorites3in7DAO userFavorites3in7DAO;
    @Override
    public List<Map<String, Object>> getFavoritesByMajor(Map<String, Object> map) {
        setUserId(map);
        return userFavorites3in7DAO.getFavoritesByMajor(map);
    }

    @Override
    public List<Map<String, Object>> getFavoritesBySubjectKey(Map<String, Object> map) {
        setUserId(map);
        return userFavorites3in7DAO.getFavoritesBySubjectKey(map);
    }

    @Override
    public List<Map<String, Object>> getFavoritesBySubject(Map<String, Object> map) {
        setUserId(map);
        return userFavorites3in7DAO.getFavoritesBySubject(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        setUserId(map);
        return userFavorites3in7DAO.count(map);
    }

    @Override
    public boolean insertFavorites(Map<String, Object> map) {
        setUserIdAndErr(map);
        return userFavorites3in7DAO.insertFavorites(map);
    }

    @Override
    public boolean deleteById(Map<String, Object> map) {
        setUserIdAndErr(map);
        return userFavorites3in7DAO.deleteById(map);
    }

    @Override
    public boolean deleteBySubjects(Map<String, Object> map) {
        setUserIdAndErr(map);
        return userFavorites3in7DAO.deleteBySubjects(map);
    }
    private void setUserIdAndErr(Map<String, Object> map){
        if(UserContext.getCurrentUser()!=null){
            map.put("userId",UserContext.getCurrentUser().getAccountId());
        }else {
            throw new BizException("1000021","请先登录!");
        }
    }

    private void setUserId(Map<String, Object> map){
        if(UserContext.getCurrentUser()!=null){
            map.put("userId",UserContext.getCurrentUser().getAccountId());
        }
    }
}
