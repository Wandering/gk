package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.domain.ExpertInfo;

import java.util.Map;

/**
 * Created by yangyongping on 2016/10/20.
 */
public interface IExpertApplyService {

    /**
     * 申请做专家
     * @return
     */
    boolean apply(ExpertInfo expertInfo);
}
