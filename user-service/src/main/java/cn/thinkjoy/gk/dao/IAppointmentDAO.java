/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  AppointmentDAO.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.dao;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Appointment;

import java.util.List;
import java.util.Map;

public interface IAppointmentDAO extends IBaseDAO<Appointment>{
    List<Appointment> like(Map<String,Object> map,String orderBy , Object sortBy);



}
