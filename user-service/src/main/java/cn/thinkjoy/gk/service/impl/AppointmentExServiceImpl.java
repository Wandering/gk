package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ex.IAppointmentExDAO;
import cn.thinkjoy.gk.domain.Appointment;
import cn.thinkjoy.gk.service.IAppointmentExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkjoy on 15/9/23.
 */
@Service("AppointmentExServiceImpl")

public class AppointmentExServiceImpl implements IAppointmentExService {
    @Autowired
    private IAppointmentExDAO appointmentExDAO;
    @Override
    public List<Appointment> queryListByTitleKey(Map<String,Object> map, String orderBy,String sortBy, Integer offset, Integer rows) {

        List<Appointment> list= appointmentExDAO.like(map, orderBy, sortBy, offset, rows);
        return list;
    }
}
