package cn.thinkjoy.gk.controller.user;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.dao.ex.IAppointmentExDAO;
import cn.thinkjoy.gk.domain.Appointment;
import cn.thinkjoy.gk.pojo.AppointmentPojo;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IAppointmentExService;
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
	private static final Logger LOGGER= LoggerFactory.getLogger(AppointmentController.class);
	@Autowired
	private IAppointmentService appointmentService;
	@Autowired
	private IAppointmentExService appointmentExService;



	@RequestMapping(value = "/getAppointment",method = RequestMethod.GET)
	@ResponseBody
	public List<AppointmentPojo> getAppointment(@RequestParam(defaultValue = "1") int  pageNo,@RequestParam(defaultValue = "10") int pageSize,String  titleKey) {
		UserAccountPojo userAccountPojo=super.getUserAccountPojo();
		if(null==userAccountPojo ||  null==userAccountPojo.getId()){
			throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
		}
		Long userId=userAccountPojo.getId();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId",userId);
		map.put("title",titleKey);
		List<Appointment> list= appointmentExService.queryListByTitleKey(map, "id", "desc",(pageNo-1)*pageSize,pageSize);
		List<AppointmentPojo> appointmentPojos=new ArrayList<AppointmentPojo>();
		for(Appointment appointment:list){
			AppointmentPojo appointmentPojo=new AppointmentPojo();
			appointmentPojo.setId(appointment.getId());
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
		UserAccountPojo userAccountPojo=super.getUserAccountPojo();
		if(null==userAccountPojo ||  null==userAccountPojo.getId()){
			throw new BizException(ERRORCODE.USER_NO_EXIST.getCode(), ERRORCODE.USER_NO_EXIST.getMessage());
		}

		Appointment appointment=new Appointment();
		appointment.setUserId(userAccountPojo.getId());
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
