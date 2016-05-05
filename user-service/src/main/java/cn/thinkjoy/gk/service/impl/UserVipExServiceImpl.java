package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IUserVipExDAO;
import cn.thinkjoy.gk.service.IUserVipExService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 16/2/18.
 */
@Service("UserVipExServiceImpl")
public class UserVipExServiceImpl implements IUserVipExService {
    @Autowired
    private IUserVipExDAO userVipExDAO;

    @Override
    public List<Map<String, String>> getVipInfoListByArea(Map<String, String> paramMap) {
        return userVipExDAO.findVipInfoListByArea(paramMap);
    }

    @Override
    public Map<String, Integer> getIndexUserCount() {
        Map<String, Integer> map = Maps.newHashMap();
        // 注册用户数(基数为36627)
        Integer registCount = userVipExDAO.getRegisteUserCount();
        map.put("registeUserCount",registCount==null?0:registCount + 36627);
        return map;
    }
}
