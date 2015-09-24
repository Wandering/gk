package cn.thinkjoy.gk.controller.guide;

import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.domain.AdmissionBatch;
import cn.thinkjoy.gk.domain.PolicyInterpretation;
import cn.thinkjoy.gk.service.IAdmissionBatchService;
import cn.thinkjoy.gk.service.IPolicyInterpretationService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 政策解读
 */
@Controller
@RequestMapping("/policyInterpretation")
public class PolicyInterpretationController extends BaseController {

    @Autowired
    private IAdmissionBatchService admissionBatchService;
    @Autowired
    private IPolicyInterpretationService policyInterpretationService;

    @RequestMapping(value = "/admissionBatchs", method = RequestMethod.GET)
    @ResponseBody
    public List<AdmissionBatch> getAdmissionBatchs() {
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("status", 1);
        List<AdmissionBatch> admissionBatchList = admissionBatchService.queryList(conditions, null, null);
        return admissionBatchList;
    }

    @RequestMapping(value = "/categorys")
    @ResponseBody
    public List<PolicyInterpretation> getPolicyInterpretationCategory(@RequestParam("batchId") long batchId,
                                                                      @RequestParam("provinceId") long provinceId) {
        return policyInterpretationService
                .findPolicyInterpretationCategoryByBatchIdAndProvinceId(batchId, provinceId);
    }

    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public PolicyInterpretation getPolicyInterpretationDetail(@PathVariable long id) {
        return (PolicyInterpretation) policyInterpretationService.fetch(id);
    }
}
