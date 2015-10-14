package cn.thinkjoy.gk.controller.user;


import cn.thinkjoy.gk.pojo.ProvincePojo;
import cn.thinkjoy.gk.service.IRegionService;
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
@Scope("prototype")
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



		return regionService.getAllRegion();
	}



}
