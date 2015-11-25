package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IGkinformationGkhotDAO;
import cn.thinkjoy.gk.domain.GkinformationGkhot;
import cn.thinkjoy.gk.service.IGkinformationGkhotService;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by ZL on 2015/9/23.
 */
@Service("GkinformationGkhotServiceImpl")
public class GkinformationGkhotServiceImpl implements IGkinformationGkhotService {
    private static Logger LOGGER = LoggerFactory.getLogger(GkinformationGkhotServiceImpl.class);
    @Autowired
    private IGkinformationGkhotDAO informationDAO;

    @Override
    public List<GkinformationGkhot> getAllInformation(long areaId,Integer offset,Integer rows) {
        List<GkinformationGkhot> list = informationDAO.getAllInformation(areaId,offset,rows);
        return list;
    }
    @Override
    public List<GkinformationGkhot> getInformationByKey(long areaId,String key,Integer offset,Integer rows) {
        List<GkinformationGkhot> list = informationDAO.getInformationByKey(areaId,key, offset, rows);
        return list;
    }

    @Override
    public GkinformationGkhot getInformationContentById(Integer id) {
        System.out.println("开始时间："+System.currentTimeMillis());
        GkinformationGkhot information = informationDAO.getInformationContentById(id);
        System.out.println("结束时间："+System.currentTimeMillis());
        return information;
    }

    @Override
    public List<GkinformationGkhot> getHotInformation(long areaId,Integer offset, Integer rows){
        List<GkinformationGkhot> list = informationDAO.getHotInformation(areaId,offset, rows);
        return list;
    }

    @Override
    public boolean updateHotInformation(Integer id) {
        boolean flag = false;
        try {
            informationDAO.updateHotCount(id);
            flag = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int saveGkinformationGkhot(GkinformationGkhot gkinformationGkhot) {
        return informationDAO.saveGkinformationGkhot(gkinformationGkhot);
    }

}
