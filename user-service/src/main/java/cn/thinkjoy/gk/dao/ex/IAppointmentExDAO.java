/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: gaokao
 * $Id:  AppointmentDAO.java 2015-09-23 10:34:36 $
 */
package cn.thinkjoy.gk.dao.ex;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.gk.domain.Appointment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IAppointmentExDAO {
  // List<Appointment> queryListByTitleKey(@Param("titleKey") String titleKey) ;
   List<Appointment> like(@Param("map") Map<String,Object> map, @Param("orderBy") String orderBy,@Param("sortBy") String sortBy, @Param("offset") Integer offset,  @Param("rows") Integer rows);



}
