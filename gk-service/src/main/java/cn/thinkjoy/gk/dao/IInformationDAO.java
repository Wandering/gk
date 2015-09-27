package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IInformationDAO {
    public List<Information> getAllInformation(@Param("offset")Integer offset,@Param("rows")Integer rows);
    public List<Information> getInformationByKey(@Param("key") String key,@Param("offset")Integer offset,@Param("rows")Integer rows);
}
