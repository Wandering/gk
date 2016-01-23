package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.DomainConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ZGKBaseController extends BaseCommonController{

	@Autowired
	private IUserAccountExService userAccountExService;

	@Autowired
	private IProvinceService provinceService;

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

	public Long getAreaCookieValue() throws Exception {
		List<Province> list =  provinceService.findAll();
		Map<String, Object> areaMap = new HashMap<>();
		for (Province province:list) {
			areaMap.put(province.getCode(), String.valueOf(province.getId()));
		}
		String areaShort = request.getParameter("userKey");
		String areaId = DomainConst.ZJ_DOMAIN_CODE;
		if(null != areaShort){
			areaId = String.valueOf(areaMap.get(areaShort));
		}
		return Long.valueOf(areaId.toString());
	}

}