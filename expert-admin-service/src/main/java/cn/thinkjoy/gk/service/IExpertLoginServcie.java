package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;

/**
 * Created by yangyongping on 2016/11/16.
 */
public interface IExpertLoginServcie {
    ExpertUserDTO login(ExpertUser expertUser);

    boolean userExist(String account);

    Boolean resetPassword(ExpertUser expertUser, String newPassword);
}
