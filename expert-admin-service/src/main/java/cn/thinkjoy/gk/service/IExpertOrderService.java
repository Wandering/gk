package cn.thinkjoy.gk.service;

import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.gk.pojo.ExpertOrderDTO;

import java.util.List;

/**
 * Created by yangyongping on 2016/11/17.
 */
public interface IExpertOrderService {

    /**
     * 查询专家订单
     * @param expertId
     * @return
     */
    BizData4Page<ExpertOrderDTO> queryOrder(Long expertId,Integer page,Integer rows);
}
