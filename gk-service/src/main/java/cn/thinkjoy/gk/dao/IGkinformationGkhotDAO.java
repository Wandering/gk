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

    /**
     * 获取高考热点列表
     * 由于列表主要为标题时间，正文内容过大，不做查询
     * @param areaId
     * @param offset
     * @param rows
     * @return
     */
    public List<GkinformationGkhot> getHotInformation(@Param("areaId")long areaId,@Param("offset")Integer offset,@Param("rows")Integer rows);
    int updateHotCount(Integer id);
    int saveGkinformationGkhot(GkinformationGkhot gkinformationGkhot);
}
