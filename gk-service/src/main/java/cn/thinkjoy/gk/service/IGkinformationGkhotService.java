package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.GkinformationGkhot;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IGkinformationGkhotService {
    public List<GkinformationGkhot> getAllInformation(long areaId,Integer offset,Integer rows);
    public List<GkinformationGkhot> getInformationByKey(long areaId,String key,Integer offset,Integer rows);
    public GkinformationGkhot getInformationContentById(Integer id);
    public List<GkinformationGkhot> getHotInformation(long areaId,Integer offset, Integer rows);
    boolean updateHotInformation(Integer id);
    int saveGkinformationGkhot(GkinformationGkhot gkinformationGkhot);
}
