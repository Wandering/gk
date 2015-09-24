

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.Appointment;

import java.util.List;

public interface IAppointmentExService {

    List<Appointment> queryListByTitleKey( String titleKey, Integer offset,  Integer rows);



}
