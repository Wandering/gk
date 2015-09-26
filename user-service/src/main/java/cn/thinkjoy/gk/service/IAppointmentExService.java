

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Appointment;

import java.util.List;
import java.util.Map;

public interface IAppointmentExService {

    List<Appointment> queryListByTitleKey( Map<String,Object> map, String orderBy,String sortBy, Integer offset,  Integer rows);



}
