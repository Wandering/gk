package cn.thinkjoy.gk.user.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.Appointment;
import cn.thinkjoy.gk.pojo.AppointmentPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller("AppointmentController")
@Scope("prototype")
@RequestMapping(value = "/appointment")
public class AppointmentController extends BaseController {
	@Autowired
	private IAppointmentService appointmentService;



	private static final Logger LOGGER= LoggerFactory.getLogger(AppointmentController.class);
	@RequestMapping(value = "/getAppointment",method = RequestMethod.GET)
	public List<AppointmentPojo> getAppointment(@RequestParam(defaultValue = "0") int start,@RequestParam(defaultValue = "10") int size) {
		UserAccountPojo userAccountPojo=super.getUserAccountPojo();
		if(null==userAccountPojo ||  null==userAccountPojo.getId()){
			throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
		}
		Long userId=userAccountPojo.getId();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId",userId);
		List<Appointment> list= appointmentService.queryList(map, "createDate", "desc");
		List<AppointmentPojo> appointmentPojos=new ArrayList<AppointmentPojo>();
		for(Appointment appointment:list){
			AppointmentPojo appointmentPojo=new AppointmentPojo();
			appointmentPojo.setTitle(appointment.getTitle());
			appointmentPojo.setCreateDate(appointment.getCreateDate());
			appointmentPojos.add(appointmentPojo);
		}
		return appointmentPojos;
	}

	/**
	 *
	 * @param appointmentPojo
	 * @return
	 */
	@RequestMapping(value = "/addAppointment", method = RequestMethod.POST)
	@ResponseBody
	public String addAppointment( AppointmentPojo appointmentPojo) {

		Appointment appointment=new Appointment();
		appointment.setTitle(appointmentPojo.getTitle());
		appointment.setStartDate(appointmentPojo.getStartDate());
		appointment.setEndDate(appointmentPojo.getEndDate());
		appointment.setContent(appointmentPojo.getContent());
		appointment.setName(appointmentPojo.getName());
		appointment.setMobile(appointmentPojo.getMobile());
		appointment.setQq(appointmentPojo.getQq());
		appointmentService.insert(appointment);
		return "success";
	}



}
