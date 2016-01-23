package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.SessionConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.AreaCookieUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class ZGKBaseController extends BaseCommonController{

	@Autowired
	private IUserAccountExService userAccountExService;

	protected UserAccountPojo getUserAccountPojo() {
		UserAccountPojo userAccountBean  = null;
		String value = request.getParameter("token");
		try{
			String uInfo = DESUtil.decrypt(value, DESUtil.key);
			String uid = DESUtil.getUserInfo(uInfo)[0];
			String key = UserRedisConst.USER_KEY + value;
			if(!RedisUtil.getInstance().exists(key)){
				userAccountBean = userAccountExService.findUserAccountPojoById(Long.parseLong(uid));
				if(null!=userAccountBean){
					RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 5L, TimeUnit.HOURS);
				}
			} else{
				userAccountBean = JSON.parseObject(RedisUtil.getInstance().get(key).toString(),UserAccountPojo.class);
			}
		}catch (Exception e)
		{
			throw new BizException("error","The token is invalid!");
		}
		return userAccountBean;
	}

	protected void setUserAccountPojo(UserAccountPojo userAccountBean,String token) throws Exception {
		if(null!=userAccountBean){
			String key = UserRedisConst.USER_KEY + token;
			RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean));
		}
	}

	protected Long getAreaCookieValue() throws Exception {
		Object areaId = session.getAttribute(SessionConst.AREA_SESSION_NAME);
		if(null==areaId){
			areaId = AreaCookieUtil.getAreaCookieValue(request);
			session.setAttribute(SessionConst.AREA_SESSION_NAME,areaId);
		}
		return Long.valueOf(areaId.toString());
	}

}