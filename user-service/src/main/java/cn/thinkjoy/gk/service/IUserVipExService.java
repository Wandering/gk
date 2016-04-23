package cn.thinkjoy.gk.service;

import java.util.List;
import java.util.Map;

/**
 * Created by liusven on 16/2/18.
 */
public interface IUserVipExService {
    List<Map<String, String>> getVipInfoListByArea(Map<String, String> paramMap);
}
