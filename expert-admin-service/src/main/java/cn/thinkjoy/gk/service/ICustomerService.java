package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.ExpertCustomerApeskDTO;
import cn.thinkjoy.gk.pojo.ExpertCustomerDTO;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/17.
 */
public interface ICustomerService {

    ExpertCustomerDTO queryBaseInfo(Long orderId);

    List<ExpertCustomerApeskDTO> queryCustomerApesk(Long orderId);
}
