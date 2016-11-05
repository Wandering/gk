package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IExpertDAO;
import cn.thinkjoy.gk.domain.ExpertOrder;
import cn.thinkjoy.gk.domain.OrderRevaluation;
import cn.thinkjoy.gk.entity.*;
import cn.thinkjoy.gk.pojo.ExpertAppraisePojo;
import cn.thinkjoy.gk.pojo.ExpertInfoPojo;
import cn.thinkjoy.gk.pojo.*;
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
    public int selectCommonQuestionCount(Map<String, Object> map) {
        return dao.selectCommonQuestionCount(map);
    }

    @Override
    public List<ExpertInfoPojo> selectExpertList(Map<String, Object> map) {
        return dao.selectExpertList(map);
    }

    @Override
    public int selectExpertListCount(Map<String, Object> map) {
        return dao.selectExpertListCount(map);
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
    public int selectVedioListCount(Map<String, Object> map) {
        return dao.selectVedioListCount(map);
    }

    @Override
    public ExpertVedio selectVedioById(Map<String, Object> map) {
        return dao.selectVedioById(map);
    }

    @Override
    public List<UserQuestion> selectQuestionList(Map<String, Object> map) {
        return dao.selectQuestionList(map);
    }

    @Override
    public int selectQuestionListCount(Map<String, Object> map) {
        return dao.selectQuestionListCount(map);
    }

    @Override
    public List<ExpertCases> selectCasesList(Map<String, Object> map) {
        return dao.selectCasesList(map);
    }

    @Override
    public int selectCasesListCount(Map<String, Object> map) {
        return dao.selectCasesListCount(map);
    }

    @Override
    public List<ExpertAppraisePojo> selectAppraiseList(Map<String, Object> map) {
        return dao.selectAppraiseList(map);
    }

    @Override
    public int selectAppraiseListCount(Map<String, Object> map) {
        return dao.selectAppraiseListCount(map);
    }


    @Override
    public List<ExpertInfoPojo> checkExpert(String commonQuestionIdString,String offset,String rows,String userId,String note){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("commonQuestionIdString",commonQuestionIdString);
        map.put("note",note);
        map.put("createDate",new Date());
        dao.insertUserCommonQuestion(map);
//        List<CommonQuestion> commonQuestionList=dao.selectCommonQuestion(map);
//        if(commonQuestionList.size()>0) {
//            String specialitys="";
//            for(CommonQuestion commonQuestion:commonQuestionList){
//                specialitys=specialitys+","+commonQuestion.getSpecialitys();
//            }
//            if(StringUtils.isNotBlank(specialitys.substring(1))){
//                Map<String,Object> map1=new HashMap<>();
//                map1.put("specialitys",specialitys.substring(1));
//                map1.put("offset",offset);
//                map1.put("rows",rows);
//                List<String> expertIdList=dao.selectExpertId(map1);
//                map1.put("expertIdList",expertIdList);
//                return dao.selectExpertListBySpecialty(map1);
//            }else {
//                //模糊匹配关键词
//                Map<String,Object> map1=new HashMap<>();
//                map1.put("configDomain","speciality");
//                List<ExpertConfig> expertConfigList=dao.selectExpertConfigList(map1);
//                specialitys="";
//                for(ExpertConfig expertConfig:expertConfigList){
//                    if(note.contains(expertConfig.getConfigValue())){
//                        specialitys=specialitys+","+expertConfig.getConfigKey();
//                    }
//                }
//                if(StringUtils.isNotBlank(specialitys)) {
//                    map1.put("specialitys", specialitys.substring(1));
//                    map1.put("offset", offset);
//                    map1.put("rows", rows);
//                    List<String> expertIdList=dao.selectExpertId(map1);
//                    map1.put("expertIdList",expertIdList);
//                    return dao.selectExpertListBySpecialty(map1);
//                }else {
//                    //无匹配，返回涉及邻域最多专家
//                    return dao.selectExpertBySpecialityMore();
//                }
//            }
//
//        }
        return null;
    }

    @Override
    public void createExpertOrderRevaluation(OrderRevaluation orderRevaluation)
    {
        dao.insertOrderRevaluation(orderRevaluation);
    }

    @Override
    public void updateExpertOrderRevaluation(OrderRevaluation orderRevaluation)
    {
        dao.updateExpertOrderRevaluation(orderRevaluation);
    }

    @Override
    public OrderRevaluation findExpertOrderRevaluationByOrderNo(String orderNo)
    {
        return dao.findExpertOrderRevaluationByOrderNo(orderNo);
    }

    public void insertUserQuestion(Map<String, Object> map) {
        dao.insertUserQuestion(map);
    }

    @Override
    public List<ServicePojo> selectServiceByExpertId(Map<String, Object> map) {
        List<ServicePojo> servicePojoList=dao.selectServiceByExpertId(map);
        if(map.containsKey("userId")) {
            List<ServiceNumberPojo> serviceNumberPojoList=dao.selectServiceByUserId(map);
            for(ServicePojo servicePojo:servicePojoList){
                for(ServiceNumberPojo serviceNumberPojo:serviceNumberPojoList) {
                    if (servicePojo.getServiceTypeId().equals(serviceNumberPojo.getServiceId())){
                        if(serviceNumberPojo.getServiceNumber()>0){
                            servicePojo.setStatus(true);
                            break;
                        }
                    }
                }
            }
        }
        return servicePojoList;
    }

    @Override
    public List<Map<String, Object>> getExpertServiceInfo(Map<String, String> paramMap)
    {
        return dao.getExpertServiceInfo(paramMap);
    }

    @Override
    public List<ExpertInfoPojo> selectFamousTeacher(Map<String, Object> map) {
        return dao.selectFamousTeacher(map);
    }

    @Override
    public List<Map<String, Object>> getExpertOrderRevaluation(Map<String, String> paramMap)
    {
        return dao.getExpertOrderRevaluation(paramMap);
    }

    @Override
    public List<Map<String, Object>> getExpertOrderList(Map<String, Object> map)
    {
        return dao.getExpertOrderList(map);
    }

    @Override
    public void test1(Map<String,Object> map){
        dao.test1(map);
    }
    @Override
    public void test2(Map<String,Object> map){
        dao.test2(map);
    }

    @Override
    public List<ExpertServiceDay> getExpertServiceDays(int expertId) {
        Integer preDay = dao.getPreDay(expertId);
        if (preDay == null){
            // 默认推后30天
            preDay = 30;
        }
        return dao.getExpertServiceDays(expertId,preDay);
    }

    @Override
    public List<ExpertServiceTime> getExpertServiceTimes(int dayId) {
        return dao.getExpertServiceTimes(dayId);
    }

    @Override
    public void insertExpertAppraise(Map<String, Object> map) {
        dao.insertExpertAppraise(map);
    }

    @Override
    public List<ServiceNumberPojo> selectServiceByUserId(Map<String, Object> map) {
        return dao.selectServiceByUserId(map);
    }
}
