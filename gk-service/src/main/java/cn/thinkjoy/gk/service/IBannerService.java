package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Banner;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
public interface IBannerService {
    public List<Banner> getBannerByType(Integer type);
}
