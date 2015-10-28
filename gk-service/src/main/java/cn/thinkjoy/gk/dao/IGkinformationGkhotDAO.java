package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.GkinformationGkhot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IGkinformationGkhotDAO{
    public List<GkinformationGkhot> getAllInformation(@Param("areaId")long areaId,@Param("offset")Integer offset,@Param("rows")Integer rows);
    public List<GkinformationGkhot> getInformationByKey(@Param("areaId")long areaId,@Param("key") String key,@Param("offset")Integer offset,@Param("rows")Integer rows);
    public GkinformationGkhot getInformationContentById(@Param("id") Integer id);
    public List<GkinformationGkhot> getHotInformation(@Param("areaId")long areaId,@Param("offset")Integer offset,@Param("rows")Integer rows);
    int updateHotCount(Integer id);
    int saveGkinformationGkhot(GkinformationGkhot gkinformationGkhot);
}
