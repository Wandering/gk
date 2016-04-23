package cn.thinkjoy.gk.test.policyInterpretation.dao;

import cn.thinkjoy.push.domain.sms.SMSCheckCode;
import cn.thinkjoy.push.service.sms.SMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springme.xml")
public class PolicyInterpretationDaoTest {

//    @Autowired
//    private IAdmissionBatchDAO admissionBatchDAO;

    @Autowired
    private SMSService smsService;

    @Test
    public void findTest(){
//        Map<String, Object> conditions = Maps.newHashMap();
//        conditions.put("status", 1);
////        List<AdmissionBatch> admissionBatchList = admissionBatchDAO.queryList(conditions, null, null);
//        List<AdmissionBatch> admissionBatchList = admissionBatchDAO.findAll();
//
//        System.out.println(admissionBatchList);
        SMSCheckCode smsCheckCode=new SMSCheckCode();
        smsCheckCode.setPhone("15389091160");
        smsCheckCode.setBizTarget("gk360");
        smsCheckCode.setCheckCode("123123");
        System.out.println("调用发送!");
        boolean smsResult =smsService.sendSMS(smsCheckCode,false);
        System.out.println("发送完成!");
        System.out.println("发送结果："+smsResult);

    }

}
