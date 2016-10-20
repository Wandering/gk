package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IExpertDAO;
import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import cn.thinkjoy.gk.service.IExpertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liusven on 2016/10/19.
 */
@Service("ExpertServiceImpl")
public class ExpertServiceImpl implements IExpertService
{
    @Autowired
    private IExpertDAO dao;

    @Override
    public void insertOrder(ExpertOrder order)
    {
        dao.insertOrder(order);
    }

    @Override
    public ExpertOrder findOrderByOrderNo(String orderNo)
    {
        return dao.findOrderByOrderNo(orderNo);
    }

    @Override
    public void updateOrder(ExpertOrder order)
    {
        dao.updateOrder(order);
    }

    @Override
    public List<CommonQuestion> selectCommonQuestion(Map<String, Object> map) {
        return dao.selectCommonQuestion(map);
    }

    @Override
    public List<ExpertInfoPojo> selectExpertList(Map<String, Object> map) {
        return dao.selectExpertList(map);
    }

    @Override
    public ExpertInfoPojo selectExpertInfo(Map<String, Object> map) {
        return dao.selectExpertInfo(map);
    }

    @Override
    public List<ExpertVedio> selectVedioList(Map<String, Object> map) {
        return dao.selectVedioList(map);
    }

    @Override
    public List<UserQuestion> selectQuestionList(Map<String, Object> map) {
        return dao.selectQuestionList(map);
    }

    @Override
    public List<ExpertCases> selectCasesList(Map<String, Object> map) {
        return dao.selectCasesList(map);
    }

    @Override
    public List<ExpertAppraise> selectAppraiseList(Map<String, Object> map) {
        return dao.selectAppraiseList(map);
    }


    @Override
    public List<ExpertInfoPojo> checkExpert(String commonQuestionIdString,String offset,String rows,String userId){
        List<String> commonQuestionIdList=new ArrayList<>();
        for(String commonQuestionId:commonQuestionIdString.split(",")){
            commonQuestionIdList.add(commonQuestionId);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("commonQuestionIdList",commonQuestionIdList);
        map.put("createDate",new Date());
        dao.insertUserCommonQuestion(map);
        List<CommonQuestion> commonQuestionList=dao.selectCommonQuestion(map);
        if(commonQuestionList.size()>0) {
            String specialitys="";
            for(CommonQuestion commonQuestion:commonQuestionList){
                specialitys=specialitys+","+commonQuestion.getSpecialitys();
            }
            Map<String,Object> map1=new HashMap<>();
            map1.put("specialitys",specialitys.substring(1));
            map1.put("offset",offset);
            map1.put("rows",rows);
            return dao.selectExpertListBySpecialty(map1);
        }else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getExpertOrderList(String userId)
    {
        return dao.getExpertOrderList(userId);
    }

}
