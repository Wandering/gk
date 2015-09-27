package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IBannerDAO;
import cn.thinkjoy.gk.domain.Banner;
import cn.thinkjoy.gk.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Service("BannerServiceImpl")
public class BannerServiceImpl implements IBannerService {
    @Autowired
    private IBannerDAO bannerDAO;

    @Override
    public List<Banner> getBannerByType(Integer type) {
        List<Banner> linkUrl = bannerDAO.getBannerByType(type);
        return linkUrl;
    }
}
