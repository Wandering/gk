package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.ExpertInfo;

/**
 * Created by yangyongping on 2016/10/20.
 */
public interface IExpertApplyDAO {

    boolean insert(ExpertInfo expertInfo);
}
