package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.domain.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IBannerDAO {
    public List<Banner> getBannerByType(Integer type);
}
