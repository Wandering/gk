package cn.thinkjoy.gk.controller;

import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.domain.AdmissionBatch;
import cn.thinkjoy.gk.domain.PolicyInterpretation;
import cn.thinkjoy.gk.dto.PolicyInterpretationCategory;
import cn.thinkjoy.gk.service.IAdmissionBatchService;
import cn.thinkjoy.gk.service.IPolicyInterpretationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 政策解读
 */
@Controller
@Scope("prototype")
@RequestMapping("/policyInterpretation")
public class PolicyInterpretationController extends BaseCommonController {

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

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<PolicyInterpretation> getPolicyInterpretationCategory(@RequestParam("batchId") long batchId,
                                                                      @RequestParam("provinceId") long provinceId) {
        return policyInterpretationService
                .findPolicyInterpretationCategoryByBatchIdAndProvinceId(batchId, provinceId);
    }

    @RequestMapping(value = "/allCategories", method = RequestMethod.GET)
    @ResponseBody
    public List<PolicyInterpretationCategory> getAllPolicyInterpretationCategory(@RequestParam("provinceId") long provinceId) {
        List<PolicyInterpretationCategory> allCategory = Lists.newArrayList();
        Map<String, Object> conditions = Maps.newHashMap();
        conditions.put("status", 1);
        List<AdmissionBatch> admissionBatchList = admissionBatchService.queryList(conditions, null, null);
        if (admissionBatchList != null) {
            for (AdmissionBatch batch: admissionBatchList) {
                PolicyInterpretationCategory category = new PolicyInterpretationCategory();
                category.setId(batch.getId());
                category.setName(batch.getName());
                List<PolicyInterpretation> policyInterpretations = policyInterpretationService
                        .findPolicyInterpretationCategoryByBatchIdAndProvinceId(batch.getId(), provinceId);
                category.setCategory(policyInterpretations);
                allCategory.add(category);
            }
        }

        return allCategory;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public PolicyInterpretation getPolicyInterpretationDetail(@RequestParam("id") long id) {
        return (PolicyInterpretation) policyInterpretationService.fetch(id);
    }
}
