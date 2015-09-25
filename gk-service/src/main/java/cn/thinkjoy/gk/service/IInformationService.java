package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Information;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IInformationService {
    public List<Information> getAllInformation(Integer offset,Integer rows);
    public List<Information> getInformationByKey(String key,Integer offset,Integer rows);
}
