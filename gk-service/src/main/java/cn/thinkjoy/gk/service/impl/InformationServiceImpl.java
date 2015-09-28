package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IInformationDAO;
import cn.thinkjoy.gk.domain.Information;
import cn.thinkjoy.gk.service.IInformationService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Service("InformationServiceImpl")
public class InformationServiceImpl implements IInformationService {
    private static Logger LOGGER = LoggerFactory.getLogger(IInformationService.class);
    @Autowired
    private IInformationDAO informationDAO;
    @Override
    public List<Information> getAllInformation(Integer offset,Integer rows) {
        List<Information> list = informationDAO.getAllInformation(offset,rows);
        return list;
    }
    @Override
    public List<Information> getInformationByKey(String key,Integer offset,Integer rows) {
        List<Information> list = informationDAO.getInformationByKey(key,offset,rows);
        return list;
    }

    @Override
    public Information getInformationContentById(Integer id) {
        Information information = informationDAO.getInformationContentById(id);
        return information;
    }
}
