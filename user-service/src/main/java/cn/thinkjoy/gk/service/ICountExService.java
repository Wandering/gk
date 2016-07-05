package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.UserCollectPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by zuohao on 15/11/2.
 */
public interface ICountExService {

    void updateCount(long projectId, String type,String operation);

}
