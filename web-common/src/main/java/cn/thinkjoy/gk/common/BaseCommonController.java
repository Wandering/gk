package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.DomainConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.CookieUtil;
import cn.thinkjoy.gk.util.DESUtil;
import cn.thinkjoy.gk.util.DomainUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaseCommonController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@Autowired
	private IProvinceService provinceService;
	private Map<String, Long> areaMap = new HashMap<>();

	@Autowired
	private IUserAccountExService userAccountExService;

	private Map<String, Long> getAreaMap()
	{
		if(areaMap.isEmpty())
		{
			initAreaInfo();
		}
		return areaMap;
	}

	private void initAreaInfo()
	{
		List<Province> list =  provinceService.findAll();
		for (Province province:list) {
			areaMap.put(province.getCode(), Long.parseLong(String.valueOf(province.getId())));
		}
	}

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
							 HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}


	/**
	 * 获取用户ID
	 * @return
     */
	public String getAccoutId(){
		String uid = "0";
		if (request.getParameter("token") == null) {
			return uid;
		}
		try{
			String value = request.getParameter("token");
			String uInfo = DESUtil.decrypt(value, DESUtil.key);
			uid = DESUtil.getUserInfo(uInfo)[0];
		}catch (Exception e)
		{
			throw new BizException("error","The token is invalid!");
		}
		return uid;
	}

	/**
	 * 获取用户信息
	 * @return
     */
	protected UserAccountPojo getUserAccountPojo() {
		UserAccountPojo userAccountBean  = null;
		String value = request.getParameter("token");
		try{
			String uInfo = cn.thinkjoy.gk.common.DESUtil.decrypt(value, cn.thinkjoy.gk.common.DESUtil.key);
			String uid = cn.thinkjoy.gk.common.DESUtil.getUserInfo(uInfo)[0];
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

	/**
	 * 获取省份ID
	 * @return
	 * @throws Exception
     */
	protected Long getAreaId() throws Exception {
		String areaShort = request.getParameter("userKey");
		//默认浙江省
		String areaId = DomainConst.ZJ_DOMAIN_CODE;
		if(null != areaShort){
			areaId = String.valueOf(getAreaMap().get(areaShort));
		}
		return Long.valueOf(areaId.toString());
	}
}