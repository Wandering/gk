package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.view.BizData4Page;
import cn.thinkjoy.common.service.impl.AbstractPageService;
import cn.thinkjoy.common.utils.SqlOrderEnum;
import cn.thinkjoy.gk.constant.ExpertAdminConst;
import cn.thinkjoy.gk.dao.IExpertOrderDAO;
import cn.thinkjoy.gk.pojo.ExpertOrderDTO;
import cn.thinkjoy.gk.pojo.ExpertReservationOrderDetailDTO;
import cn.thinkjoy.gk.service.IExpertOrderService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/11/17.
 */
@Service
public class ExpertOrderServiceImpl extends AbstractPageService<IBaseDAO<ExpertReservationOrderDetailDTO>, ExpertReservationOrderDetailDTO> implements IExpertOrderService {

    @Autowired
    private IExpertOrderDAO expertOrderDAO;
    /**
     * 查询专家订单
     *
     * @param expertId
     * @param page
     * @param rows     @return
     */
    @Override
    public BizData4Page<ExpertOrderDTO> queryOrder(Long expertId, Integer page, Integer rows) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("expertId",expertId);
        BizData4Page bizData4Page =new BizData4Page();
        bizData4Page.setConditions(map);
        bizData4Page.setPage(page);
        this.queryPageByDataPerm(bizData4Page, "ord.status asc,ord.create_date", SqlOrderEnum.ASC);

        List<ExpertReservationOrderDetailDTO> expertReservationOrderDetailDTOs =bizData4Page.getRows();

        if (expertReservationOrderDetailDTOs != null && expertReservationOrderDetailDTOs.size() != 0)
        {
            //后续操作
            handlerOrder(expertReservationOrderDetailDTOs);
        }
        if (expertReservationOrderDetailDTOs != null && expertReservationOrderDetailDTOs.size() != 0)
        {
            //后续操作
            bizData4Page.setRows(detailsToDtos(expertReservationOrderDetailDTOs));
        }
        bizData4Page.setConditions(null);
        return bizData4Page;
    }



    @Override
    public IBaseDAO getDao() {
        return expertOrderDAO;
    }

    private List<ExpertOrderDTO> detailsToDtos(List<ExpertReservationOrderDetailDTO> detailDTOs){
        List<ExpertOrderDTO> dtos = new ArrayList<>();
        for (ExpertReservationOrderDetailDTO expertReservationOrderDetailDTO:detailDTOs){
            dtos.add(detailToDto(expertReservationOrderDetailDTO));
        }
        return dtos;
    }
    private ExpertOrderDTO detailToDto(ExpertReservationOrderDetailDTO detailDTO){
        ExpertOrderDTO expertOrderDTO = new ExpertOrderDTO();
        expertOrderDTO.setServiceName(detailDTO.getServiceName());
        expertOrderDTO.setServiceState(detailDTO.getStatus());
        expertOrderDTO.setChannel(detailDTO.getChannel());
        expertOrderDTO.setId(detailDTO.getId());
        expertOrderDTO.setCustomer(detailDTO.getContactPerson());
        expertOrderDTO.setCustomerId(detailDTO.getUserId());
        expertOrderDTO.setServiceTime(detailDTO.getExpectTime());
        expertOrderDTO.setServiceId(detailDTO.getServiceItem());
        return expertOrderDTO;
    }


    private void handlerOrder(List<ExpertReservationOrderDetailDTO> list)
    {
        for (ExpertReservationOrderDetailDTO expertReservationOrderDetailDTO : list)
        {
            handlerOrderStatus(expertReservationOrderDetailDTO);
        }
    }

    /**
     * 处理订单状态 0未预约  1:预约成功  2:服务中 3:结束
     *
     * @param expertReservationOrderDetailDTO
     * @return
     */
    private void handlerOrderStatus(ExpertReservationOrderDetailDTO expertReservationOrderDetailDTO)
    {
        if (expertReservationOrderDetailDTO == null)
        {
            return;
        }
        if (expertReservationOrderDetailDTO.getEndTime() == null)
        {
            return;
        }
        String orderTime = expertReservationOrderDetailDTO.getExpectTime();
        String endTime = expertReservationOrderDetailDTO.getEndTime();
        String startTime = orderTime.substring(0, orderTime.lastIndexOf("~"));
        DateFormat dateFormat = new SimpleDateFormat(ExpertAdminConst.formatString);
        Long currTime = System.currentTimeMillis();
        Long lStartTime = 0L;
        try
        {
            lStartTime = dateFormat.parse(startTime).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Long lEndTime = 0L;
        try
        {
            lEndTime = dateFormat.parse(endTime).getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Integer status = null;
        try
        {
            if (ExpertAdminConst.EXPERT_ORDER_STATUS_Y4 != expertReservationOrderDetailDTO.getStatus())
            {
                if (lStartTime != 0L && currTime - lStartTime < 0)
                {
                    //预约成功
                    status = ExpertAdminConst.EXPERT_ORDER_STATUS_Y1;
                }
                if (lStartTime != 0L && currTime - lStartTime > 0)
                {
                    //服务中
                    status = ExpertAdminConst.EXPERT_ORDER_STATUS_Y2;
                }
                if (lEndTime != 0L && currTime - lEndTime > 0)
                {
                    //结束
                    status = ExpertAdminConst.EXPERT_ORDER_STATUS_Y3;
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", expertReservationOrderDetailDTO.getId());
                map.put("status", status);
                expertOrderDAO.updateMap(map);
            }
            else
            {
                status = ExpertAdminConst.EXPERT_ORDER_STATUS_Y4;
            }
        }
        catch (Exception e)
        {
            return;
        }
        expertReservationOrderDetailDTO.setStatus(status);
    }
}
