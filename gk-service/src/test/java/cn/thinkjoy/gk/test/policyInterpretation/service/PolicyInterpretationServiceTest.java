package cn.thinkjoy.gk.test.policyInterpretation.service;

import cn.thinkjoy.gk.dao.IAdmissionBatchDAO;
import cn.thinkjoy.gk.domain.AdmissionBatch;
import cn.thinkjoy.gk.service.IAdmissionBatchService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springme.xml")
public class PolicyInterpretationServiceTest {

    @Autowired
    private IAdmissionBatchService admissionBatchService;

    @Test
    public void findTest(){
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("status", 1);
        List<AdmissionBatch> admissionBatchList = admissionBatchService.queryList(conditions, null, null);

        System.out.println(admissionBatchList);
    }
}
