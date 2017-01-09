package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.ICustomerDAO;
import cn.thinkjoy.gk.pojo.ExpertCustomerApeskDTO;
import cn.thinkjoy.gk.pojo.ExpertCustomerDTO;
import cn.thinkjoy.gk.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/18.
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerDAO customerDAO;
    @Override
    public ExpertCustomerDTO queryBaseInfo(Long orderId) {
        return customerDAO.queryBaseInfo(orderId);
    }

    @Override
    public List<ExpertCustomerApeskDTO> queryCustomerApesk(Long orderId) {
        return customerDAO.queryCustomerApesk(orderId);
    }
}
