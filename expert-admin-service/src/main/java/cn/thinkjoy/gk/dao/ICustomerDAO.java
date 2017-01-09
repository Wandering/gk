package cn.thinkjoy.gk.dao;

import cn.thinkjoy.gk.pojo.ExpertCustomerApeskDTO;
import cn.thinkjoy.gk.pojo.ExpertCustomerDTO;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/18.
 */
public interface ICustomerDAO {
    ExpertCustomerDTO queryBaseInfo(Long orderId);

    List<ExpertCustomerApeskDTO> queryCustomerApesk(Long orderId);
}
