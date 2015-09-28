package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IDataDictDAO;
import cn.thinkjoy.gk.domain.UniversityDict;
import cn.thinkjoy.gk.service.IDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wpliu on 15/9/27.
 */
@Service("DataDictServiceImpl")
public class DataDictServiceImpl implements IDataDictService {
    @Autowired
    private IDataDictDAO iDataDictDao;


    @Override
    public List<Map<String, Object>> queryDictList(Map<String, Object> map) {
        return iDataDictDao.queryDictList(map);
    }
}
