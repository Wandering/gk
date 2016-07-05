package cn.thinkjoy.gk.controller;


import cn.thinkjoy.cloudstack.cache.RedisRepository;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.ProvincePojo;
import cn.thinkjoy.gk.service.IRegionService;
import cn.thinkjoy.gk.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/region")
public class RegionController {

	private static final Logger LOGGER= LoggerFactory.getLogger(RegionController.class);
	@Autowired
	private IRegionService regionService;

	/**
	 * 获取所有地区
	 * @return
	 */
	@RequestMapping(value = "/getAllRegion" , method = RequestMethod.GET)
	@ResponseBody
	public List<ProvincePojo> getAllRegion() {
		String key = "zgk_regionList";
		RedisRepository rep = RedisUtil.getInstance();
		List<ProvincePojo> regionList = null;
		if(rep.exists(key))
		{
			return (List<ProvincePojo>) rep.get(key);
		}
		regionList = regionService.getAllRegion();
		rep.set("zgk_regionList", regionList);
		return regionList;
	}

}
