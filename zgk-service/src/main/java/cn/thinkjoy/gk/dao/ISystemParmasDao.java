package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.entity.SystemParmas;

import java.util.Map;

/**
 * Created by douzy on 16/3/14.
 */
public interface ISystemParmasDao extends IBaseDAO<SystemParmas> {
    /**
     * 获取系统参数
     * @param map
     * @return
     */
    public SystemParmas selectModel(Map map);
}
