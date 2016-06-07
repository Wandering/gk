package cn.thinkjoy.gk.service.information;

import cn.thinkjoy.gk.common.AreaMaps;
import cn.thinkjoy.gk.service.information.base.BaseCommonService;
import cn.thinkjoy.gk.service.information.service.ex.IAgentExService;
import cn.thinkjoy.zgk.common.QueryUtil;
import cn.thinkjoy.zgk.domain.BizData4Page;
import cn.thinkjoy.zgk.remote.IGkPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by admin on 2016/1/4.
 */
@Service("GkPhoneServiceImpl")
public class GkPhoneServiceImpl extends BaseCommonService implements IGkPhoneService {

    //设置是否加载内容，默认不加载
    private boolean isIgnore=false;
    @Autowired
    IAgentExService agentExService;
    @Autowired
    AreaMaps areaMaps;
    /**
     * 获取热点摘要列表 四个
     * @return
     */
    @Override
    public BizData4Page getGkPhoneList(Map<String, Object> conditions,Integer page,Integer rows) {
        return doPage(conditions,agentExService,page,rows);
    }

    @Override
    protected void enhanceSearchFilter(Map<String, Object> conditions) {
        Long area=areaMaps.getAreaId();
        if(area!=null) {
            QueryUtil.setMapOp(conditions, "areaId", "=", area);
        }
    }
}
