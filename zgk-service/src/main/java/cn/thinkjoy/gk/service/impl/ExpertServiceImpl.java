package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IExpertDAO;
import cn.thinkjoy.gk.dao.IExpertProductServiceExDAO;
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

    @Autowired
    private IExpertProductServiceExDAO iExpertProductServiceExDAO;

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
        List<ExpertInfoPojo> expertInfoPojoList=dao.selectExpertList(map);
        List<String> serviceStringList=dao.selectServiceNameByAreaId(map);
        if (expertInfoPojoList.size()>0) {
            for (ExpertInfoPojo expertInfoPojo : expertInfoPojoList) {
                String serviceTmp = expertInfoPojo.getService();
                if (StringUtils.isNotBlank(serviceTmp)) {
                    String service = "";
                    for (String serviceString : serviceStringList) {
                        if (serviceTmp.contains(serviceString)) {
                            service = service + "," + serviceString;
                        }
                    }
                    if (service.length() > 0) {
                        expertInfoPojo.setService(service.substring(1));
                    }
                }
            }
        }else {
            List<ExpertInfoPojo> expertInfoPojoList1=dao.selectExpertBySpecialityMore();
            for (ExpertInfoPojo expertInfoPojo:expertInfoPojoList1){
                Map<String,Object> map1=new HashMap<>();
                map1.put("expertId",expertInfoPojo.getExpertId());
                ExpertInfoPojo expertInfoPojo1=dao.selectExpertInfo(map1);
                if(expertInfoPojo1!=null) {
                    String serviceTmp=expertInfoPojo1.getService();
                    if (StringUtils.isNotBlank(serviceTmp)) {
                        String service = "";
                        for (String serviceString : serviceStringList) {
                            if (serviceTmp.contains(serviceString)) {
                                service = service + "," + serviceString;
                            }
                        }
                        if (service.length() > 0) {
                            expertInfoPojo1.setService(service.substring(1));
                        }
                    }
                    expertInfoPojoList.add(expertInfoPojo1);
                }
            }
        }

        return expertInfoPojoList;
    }

    @Override
    public boolean hasService(Map<String,Object> map){
        if(dao.hasService(map)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<ExpertInfoPojo> checkExpertByProduct(Map<String, Object> map) {
        List<ExpertInfoPojo> expertInfoPojoList=dao.checkExpertByProduct(map);
        List<ExpertInfoPojo> returnExpertInfoPojoList=new ArrayList<>();
        List<String> serviceStringList=dao.selectServiceNameByAreaId(map);
        if(expertInfoPojoList.size()>0){ //可以正常推出专家
            for(ExpertInfoPojo expertInfoPojo:expertInfoPojoList){
                Map<String,Object> map1=new HashMap<>();
                map1.put("expertId",expertInfoPojo.getExpertId());
                ExpertInfoPojo expertInfoPojo1=dao.selectExpertInfo(map1);
                if(expertInfoPojo1!=null) {
                    String serviceTmp=expertInfoPojo1.getService();
                    if (StringUtils.isNotBlank(serviceTmp)) {
                        String service = "";
                        for (String serviceString : serviceStringList) {
                            if (serviceTmp.contains(serviceString)) {
                                service = service + "," + serviceString;
                            }
                        }
                        if (service.length() > 0) {
                            expertInfoPojo1.setService(service.substring(1));
                        }
                    }
                    returnExpertInfoPojoList.add(expertInfoPojo1);
                }
            }
        }else {//没有服务对应的专家
            List<ExpertInfoPojo> expertInfoPojoList1=dao.selectExpertBySpecialityMore();
            for (ExpertInfoPojo expertInfoPojo:expertInfoPojoList1){
                Map<String,Object> map1=new HashMap<>();
                map1.put("expertId",expertInfoPojo.getExpertId());
                ExpertInfoPojo expertInfoPojo1=dao.selectExpertInfo(map1);
                if(expertInfoPojo1!=null) {
                    String serviceTmp=expertInfoPojo1.getService();
                    if (StringUtils.isNotBlank(serviceTmp)) {
                        String service = "";
                        for (String serviceString : serviceStringList) {
                            if (serviceTmp.contains(serviceString)) {
                                service = service + "," + serviceString;
                            }
                        }
                        if (service.length() > 0) {
                            expertInfoPojo1.setService(service.substring(1));
                        }
                    }
                    returnExpertInfoPojoList.add(expertInfoPojo1);
                }
            }
        }
        return returnExpertInfoPojoList;
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
    public String checkProduct(String commonQuestionIdString,String offset,String rows,String userId,String note,String areaId){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("commonQuestionIdString",commonQuestionIdString);
        map.put("note",note);
        map.put("createDate",new Date());
        dao.insertUserCommonQuestion(map);
        List<CommonQuestion> commonQuestionList=dao.selectCommonQuestion(map);
        if(commonQuestionList.size()>0) {
            String specialitys="";
            Map<String,Object> map1=new HashMap<>();
            if (!areaId.equals("330000")) {
                areaId="0";
            }
            map1.put("areaId",areaId);
            map1.put("offset",offset);
            map1.put("rows",rows);
            for(CommonQuestion commonQuestion:commonQuestionList){
                    specialitys = specialitys + "," + commonQuestion.getSpecialitys();
            }
            if(StringUtils.isNotEmpty(specialitys.substring(1))){
                if (commonQuestionList.size()==1) {
                    map1.put("specialitys", specialitys.substring(1));
                    ProductPojo product = dao.selectProductByServiceIdAndAreaId(map1).get(0);
                    return product.getProductId();
                }else {//多选的情况
                    Map<String,Integer> productIdNumber=new HashMap<>();
                    for(CommonQuestion commonQuestion:commonQuestionList){//每一个问题对应的服务查询一次
                        map1.put("specialitys", commonQuestion.getSpecialitys());
                        map1.remove("offset");
                        List<ProductPojo> productList = dao.selectProductByServiceIdAndAreaId(map1);
                        for(ProductPojo productPojo:productList){
                            String productId=productPojo.getProductId();
                            if(!productIdNumber.containsKey(productId)){
                                productIdNumber.put(productId,0);
                            }
                            productIdNumber.put(productId,productIdNumber.get(productId)+1);
                        }
                    }
                    int productIdNumberMax=0;
                    for(String productId:productIdNumber.keySet()){//找出匹配次数最多的一个
                        if(productIdNumberMax<productIdNumber.get(productId)){
                            productIdNumberMax=productIdNumber.get(productId);
                        }
                    }
                    String productIdMaxList="";//保存所有匹配次数最多的卡Id
                    for(String productId:productIdNumber.keySet()){
                        if(productIdNumber.get(productId)==productIdNumberMax){
                            productIdMaxList=productIdMaxList+","+productId;
                        }
                    }
                    //查询productIdMaxList中所有卡最便宜的卡
                    map1.put("productIdList",productIdMaxList.substring(1));
                    return dao.selectCheapProductIdByProductIdList(map1);
                }
            }
            else {
                //模糊匹配关键词
                map1.put("configDomain","expertService");
                List<ExpertConfig> expertConfigList=dao.selectExpertConfigList(map1);
                specialitys="";
                for(ExpertConfig expertConfig:expertConfigList){
                    if(note.contains(expertConfig.getConfigValue())){
                        specialitys=specialitys+","+expertConfig.getConfigKey();
                    }
                }
                if (StringUtils.isBlank(specialitys)) {
                    for (ExpertConfig expertConfig : expertConfigList) {
                        if (expertConfig.getConfigValue().contains(note)) {
                            specialitys = specialitys + "," + expertConfig.getConfigKey();
                        }
                    }
                }
                if(StringUtils.isNotBlank(specialitys)) {
                    map1.put("specialitys", specialitys.substring(1));
                    ProductPojo product=dao.selectProductByServiceIdAndAreaId(map1).get(0);
                    return product!=null?product.getProductId():null;
                }else {
                    //无匹配，返回涉及邻域最多卡
                    ProductPojo product=dao.selectProductByServiceIdAndAreaId(map1).get(0);
                    return product!=null?product.getProductId():null;
                }
            }
        }
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
    public void test3(Map<String,Object> map){
        dao.test3(map);
    }

    @Override
    public List<ExpertServiceDay> getExpertServiceDays(int expertId) {
        Integer preDay = dao.getPreDay(expertId);
        if (preDay == null){
            // 默认推后30天
            preDay = 30;
        }
        return dao.getExpertServiceDays(expertId,-preDay);
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

    @Override
    public ExpertChannel getChannelByexpertIdAndStuId(long expertId, long stuId, int type) {
        return dao.getChannelByexpertIdAndStuId(expertId,stuId,type);
    }

    @Override
    public ExpertChannel getChannelByCid(String cid) {
        return dao.getChannelByCid(cid);
    }

    @Override
    public void updateChannelByCid(String cid) {
        dao.updateChannelByCid(cid,System.currentTimeMillis());
    }

    @Override
    public void insertChannel(ExpertChannel channel) {
        dao.insertChannel(channel);
    }
}
