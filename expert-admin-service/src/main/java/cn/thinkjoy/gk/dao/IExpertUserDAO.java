package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.ExpertUser;
import cn.thinkjoy.gk.pojo.ExpertUserDTO;

/**
 * Created by yangyongping on 2016/11/16.
 */
public interface IExpertUserDAO {

    ExpertUser queryBaseUserByAccount(String account);

    ExpertUserDTO queryUserInfoByAccount(String account);
}
