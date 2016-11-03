package cn.thinkjoy.gk.common;

import cn.thinkjoy.gk.constant.UserRedisConst;
import cn.thinkjoy.gk.domain.Province;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.ModelUtil;
import cn.thinkjoy.gk.service.IProvinceService;
import cn.thinkjoy.gk.util.RedisUtil;
import cn.thinkjoy.gk.util.UserContext;
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
		UserAccountPojo pojo = UserContext.getCurrentUser();
		if(pojo == null){
			ModelUtil.throwException(ERRORCODE.USER_UN_LOGIN);
		}
		return pojo.getId().toString();
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
			try{
				RedisUtil.getInstance().set(key, JSON.toJSONString(userAccountBean), 4l, TimeUnit.HOURS);
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
		try{
			return Long.valueOf(String.valueOf(getAreaMap().get(UserAreaContext.getCurrentUserArea())).toString());
		}catch (Exception e){
			return Long.valueOf(String.valueOf(getAreaMap().get("zj")).toString());
		}
	}

}
