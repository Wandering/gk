package cn.thinkjoy.gk.service.selcourse.impl;

import cn.thinkjoy.gk.dao.ICollectionDAO;
import cn.thinkjoy.gk.pojo.MajorPojo;
import cn.thinkjoy.gk.service.selcourse.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/12.
 */
@Service
public class CollectionServiceImpl implements ICollectionService {

    @Autowired
    private ICollectionDAO collectionDAO;

    @Override
    public boolean save(Map<String, Object> map) {
        return collectionDAO.save(map)>0;
    }

    @Override
    public boolean check(Map<String, Object> map) {
        return collectionDAO.check(map)>0;
    }

    @Override
    public boolean delete(Object id,Object userId) {
        return collectionDAO.delete(id,userId)>0;
    }

    @Override
    public List<MajorPojo> queryPage(Map<String, Object> map) {
        return collectionDAO.queryPage(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return collectionDAO.count(map);
    }
}
