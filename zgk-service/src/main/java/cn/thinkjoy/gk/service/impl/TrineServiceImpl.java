package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.selcourse.ITrineDAO;
import cn.thinkjoy.gk.service.ITrineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/10/10.
 */
@Service
public class TrineServiceImpl implements ITrineService{
    @Autowired
    private ITrineDAO trineDAO;
    /**
     * 分页方法
     *
     * @param conditions
     * @return
     */
    @Override
    public List queryPage(Map<String, Object> conditions,int offset, int rows,String orderBy,String sortBy) {
        return trineDAO.queryPage(conditions,offset,rows,
                orderBy,sortBy,null);
    }
    @Override
    public List queryPage(Map<String, Object> conditions,int offset, int rows,String orderBy,String sortBy,Map<String, Object> selector) {
        return trineDAO.queryPage(conditions,offset,rows,
                orderBy,sortBy,selector);
    }

    /**
     * 分页统计方法
     *
     * @param conditions
     * @return
     */
    @Override
    public int count(Map<String, Object> conditions) {
        return trineDAO.count(conditions);
    }


    @Override
    public List queryYear() {
        return trineDAO.queryYear();
    }

    @Override
    public List queryMajorType() {
        return trineDAO.queryMajorType();
    }

    @Override
    public List queryBatchName() {
        return trineDAO.queryBatchName();
    }

    /**
     * 根据id查章程
     *
     * @param id
     * @return
     */
    @Override
    public String getUniversityIntroById(String id) {
        return trineDAO.getUniversityIntroById(id);
    }
}
