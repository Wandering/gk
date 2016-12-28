package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.common.ConvertUtil;
import cn.thinkjoy.gk.common.ErrorCode;
import cn.thinkjoy.gk.common.ExceptionUtil;
import cn.thinkjoy.gk.dao.IExpertInfoDAO;
import cn.thinkjoy.gk.dao.IExpertResourcesDAO;
import cn.thinkjoy.gk.dao.IExpertUserDAO;
import cn.thinkjoy.gk.domain.ExpertInfo;
import cn.thinkjoy.gk.domain.ExpertResources;
import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;
import cn.thinkjoy.gk.service.IExpertLoginServcie;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/16.
 */
@Service
public class ExpertLoginServiceImpl implements IExpertLoginServcie {
    @Autowired
    private IExpertUserDAO expertUserDAO;
    @Autowired
    private IExpertInfoDAO expertInfoDAO;
    @Autowired
    private IExpertResourcesDAO expertResourcesDAO;
    @Override
    public ExpertUserDTO login(ExpertUser expertUser) {
        ExpertUser user = expertUserDAO.queryBaseUserByAccount(expertUser.getAccount());
        if (user == null) {
            ExceptionUtil.throwException(ErrorCode.ACCOUNT_ERROR);
        }else if (!user.getPassword().equals(expertUser.getPassword())){
            ExceptionUtil.throwException(ErrorCode.PWD_ERROR);
        }
        ExpertUserDTO userInfo = expertUserDAO.queryUserInfoByAccount(expertUser.getAccount());

        // 根据用户ID查找用户所有菜单权限
        List<ExpertResources> resources = Lists.newArrayList();

            resources = expertResourcesDAO.findAll("id","ASC");

        userInfo.setMeuns(ConvertUtil.convertMeuns(resources));
        return userInfo;
    }

    @Override
    public boolean userExist(String account) {
        return expertUserDAO.queryBaseUserByAccount(account)==null;
    }

    @Override
    public Boolean resetPassword(ExpertUser expertUser, String newPassword) {
        ExpertUser user = expertUserDAO.queryBaseUserByAccount(expertUser.getAccount());
        ExpertInfo expertInfo = new ExpertInfo();
        expertInfo.setPassword(newPassword);
        expertInfo.setId(user.getId());
        return expertInfoDAO.update(expertInfo)>0;
    }
}
