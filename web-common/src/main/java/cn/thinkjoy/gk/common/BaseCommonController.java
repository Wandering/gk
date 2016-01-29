package cn.thinkjoy.gk.common;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.constant.DomainConst;
import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.service.IUserAccountExService;
import cn.thinkjoy.gk.util.DESUtil;
import cn.thinkjoy.gk.util.RedisUtil;
import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

	private String areaShort;
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
		this.setAreaShort(request.getParameter("userKey"));
	}


	/**
	 * 获取用户ID
	 * @return
     */
	public String getAccoutId(){
		String uid = null;
		String  token= request.getParameter("token");
		if (null == token || "".equals(token)) {
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
		if(null == value || "".equals(value))
		{
			return userAccountBean;
		}
		try{
			String uInfo = cn.thinkjoy.gk.common.DESUtil.decrypt(value, cn.thinkjoy.gk.common.DESUtil.key);
			String uid = cn.thinkjoy.gk.common.DESUtil.getUserInfo(uInfo)[0];
			String key = UserRedisConst.USER_KEY + value;
			if(!RedisUtil.getInstance().exists(key))
			{
				userAccountBean = userAccountExService.findUserAccountPojoById(Long.parseLong(uid));
				if(null!=userAccountBean)
				{
					RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 4L, TimeUnit.HOURS);
				}
				else
				{
					throw new BizException("error","The token is invalid!");
				}
			}
			else
			{
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
			try{
				RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 4L, TimeUnit.HOURS);
			}catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * 获取省份ID
	 * @return
	 */
	protected Long getAreaId(){
		//默认浙江省
		return Long.valueOf(String.valueOf(getAreaMap().get(this.getAreaShort())).toString());
	}

	public String getAreaShort() {
		return areaShort;
	}

	public void setAreaShort(String areaShort) {
		//默认浙江省
		if(null == areaShort){
			areaShort=DomainConst.ZJ_DOMAIN;
		}
		this.areaShort = areaShort;
		UserAreaContext.setCurrentUserArea(this.areaShort);
	}
}