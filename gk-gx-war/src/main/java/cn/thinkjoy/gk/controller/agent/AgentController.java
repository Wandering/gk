package cn.thinkjoy.gk.controller.agent;


import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.Agent;
import cn.thinkjoy.gk.pojo.AgentPojo;
import cn.thinkjoy.gk.service.IAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("prototype")
@RequestMapping(value = "/agent")
public class AgentController extends BaseController{

	private static final Logger LOGGER= LoggerFactory.getLogger(AgentController.class);
	@Autowired
	private IAgentService agentService;

	/**
	 * 获取服务提供商
	 * @return
	 */
	@RequestMapping(value = "/getAgent" , method = RequestMethod.GET)
	@ResponseBody
	public List<AgentPojo> getAgent() throws Exception{
		long areaId = getAreaCookieValue();
		List<Agent> list=  agentService.findList("areaId",areaId);
		List<AgentPojo> agentPojos=new ArrayList<AgentPojo>();
		for(Agent agent:list){
			AgentPojo agentPojo=new AgentPojo();
			agentPojo.setAddress(agent.getAddress());
			agentPojo.setName(agent.getName());
			agentPojo.setTelphone(agent.getTelphone());
			agentPojos.add(agentPojo);
		}

		return agentPojos;
	}



}
