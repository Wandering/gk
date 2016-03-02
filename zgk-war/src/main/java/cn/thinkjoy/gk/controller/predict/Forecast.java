package cn.thinkjoy.gk.controller.predict;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.common.ERRORCODE;
import cn.thinkjoy.gk.common.IForecase;
import cn.thinkjoy.gk.service.IForecastService;
import cn.thinkjoy.gk.service.IUserInfoExService;
import cn.thinkjoy.gk.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/3/1.
 */
@Component("forecast")
public class Forecast implements IForecase{
    @Autowired
    IForecastService forecastService;
    @Autowired
    private IUserInfoExService userInfoExService;

    public Object getLastoFrecast(String uid){
        Map<String, Object> selector=new HashMap<>();
        selector.put("type","type");
        selector.put("typeId","typeId");
        selector.put("universityName","universityName");
        selector.put("achievement","achievement");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",uid);
        return forecastService.queryOne(map,"lastModDate", SqlOrderEnum.DESC,selector);
    }

    @Override
    public boolean isFrecast() {
        Long uid = UserContext.getCurrentUser().getId();
        if(uid==null){
            throw new BizException(ERRORCODE.ISLOGIN.getCode(),ERRORCODE.ISLOGIN.getMessage());
        }
        return userInfoExService.isPredictByUid(uid);
    }
}
