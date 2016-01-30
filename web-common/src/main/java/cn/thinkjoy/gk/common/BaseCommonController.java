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
import cn.thinkjoy.gk.util.UserContext;
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
		Long uid=UserContext.getCurrentUser().getId();
		return uid.toString();
	}

	/**
	 * 获取用户信息
	 * @return
     */
	protected UserAccountPojo getUserAccountPojo() {
		return UserContext.getCurrentUser();
	}

	protected void setUserAccountPojo(UserAccountPojo userAccountBean,String token) throws Exception {
		if(null!=userAccountBean){
			String key = UserRedisConst.USER_KEY + token;
			System.out.println("hahaha");
			try{
				RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean));
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
		return Long.valueOf(String.valueOf(getAreaMap().get(UserAreaContext.getCurrentUserArea())).toString());
	}

}