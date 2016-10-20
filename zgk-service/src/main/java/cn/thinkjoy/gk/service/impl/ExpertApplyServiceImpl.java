package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IExpertApplyDAO;
import cn.thinkjoy.gk.domain.ExpertInfo;
import cn.thinkjoy.gk.service.IExpertApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yangyongping on 2016/10/20.
 */
@Service("expertApplyServiceImpl")
public class ExpertApplyServiceImpl implements IExpertApplyService{
    @Autowired
    private IExpertApplyDAO expertApplyDAO;
    /**
     * 申请做专家
     *
     * @return
     */
    @Override
    public boolean apply(ExpertInfo expertInfo) {

        return expertApplyDAO.insert(expertInfo);
    }
}
