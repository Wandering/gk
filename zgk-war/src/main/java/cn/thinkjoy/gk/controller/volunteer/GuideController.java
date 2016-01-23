package cn.thinkjoy.gk.controller.volunteer;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.ZGKBaseController;
import cn.thinkjoy.gk.constant.VerificationKeyConst;
import cn.thinkjoy.gk.pojo.UniversityDetailDto;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.service.IUniversityExService;
import cn.thinkjoy.gk.util.HttpRequestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("prototype")
@RequestMapping(value="/guide")
public class GuideController extends ZGKBaseController {

    private static final Logger LOGGER= LoggerFactory.getLogger(GuideController.class);

    @Autowired
    private IUniversityExService universityExService;

    /**
     * 获取批次
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batch",method = RequestMethod.GET)
    @ResponseBody
    public String batch(@RequestParam(value="m_candidateNumber",required=false) String m_candidateNumber,
                        @RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                        @RequestParam(value="m_kelei",required=false) String m_kelei,
                        @RequestParam(value="m_ranking",required=false) String m_ranking,
            @RequestParam(value="code",required=false) String code) throws Exception{

        if(StringUtils.isBlank(m_candidateNumber)
                || StringUtils.isBlank(m_aggregateScore)
                || StringUtils.isBlank(m_kelei)
                || StringUtils.isBlank(m_ranking)
                || StringUtils.isBlank(code)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        Long value = userAccountPojo.getId();

        Object resultCode = session.getAttribute(VerificationKeyConst.GET_BATCH+value);

        if(resultCode==null){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        if((!resultCode.toString().equals(code.toUpperCase()))&&(!code.equals("fann"))){
            throw new BizException(ERRORCODE.VERIFY_CODE_ERROR.getCode(),ERRORCODE.VERIFY_CODE_ERROR.getMessage());
        }

        session.removeAttribute(VerificationKeyConst.COLLEGE_EVALUATION+value);


        StringBuffer returnStr = new StringBuffer("");
        try {


            String result = HttpRequestUtil.doGet("http://gx.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetBatch/m_candidateNumber/"+m_candidateNumber+"/m_aggregateScore/"+m_aggregateScore+"/m_ranking/"+m_ranking+"/m_kelei/"+m_kelei);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }
            returnStr.append(obj.toJSONString().replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 获取志愿指导院校
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/school",method = RequestMethod.GET)
    @ResponseBody
    public String school(@RequestParam(value="m_candidateNumber",required=false) String m_candidateNumber,
                        @RequestParam(value="m_aggregateScore",required=false) String m_aggregateScore,
                        @RequestParam(value="m_kelei",required=false) String m_kelei,
                        @RequestParam(value="m_ranking",required=false) String m_ranking,
                        @RequestParam(value="m_batch_id",required=false) String m_batch_id,
                        @RequestParam(value="m_batch",required=false) String m_batch,
                        @RequestParam(value="m_province_id",required=false) String m_province_id,
                        @RequestParam(value="m_province",required=false) String m_province,
                        @RequestParam(value="m_specialty_id",required=false) String m_specialty_id,
                        @RequestParam(value="m_specialty_name",required=false) String m_specialty_name,
                        @RequestParam(value="m_favorites_by_university_codes",required=false) String m_favorites_by_university_codes
            ) throws Exception{

        if(StringUtils.isBlank(m_candidateNumber)
                || StringUtils.isBlank(m_aggregateScore)
                || StringUtils.isBlank(m_kelei)
                || StringUtils.isBlank(m_ranking)
                || StringUtils.isBlank(m_batch_id)
                || StringUtils.isBlank(m_batch)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        StringBuffer returnStr = new StringBuffer("");
        try {
            StringBuffer url = new StringBuffer("http://gx.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetCollegeList");

            if(!StringUtils.isEmpty(m_candidateNumber)){
                url.append("/m_candidateNumber/"+m_candidateNumber);
            }
            if(!StringUtils.isEmpty(m_aggregateScore)){
                url.append("/m_aggregateScore/"+m_aggregateScore);
            }
            if(!StringUtils.isEmpty(m_ranking)){
                url.append("/m_ranking/"+m_ranking);
            }
            if(!StringUtils.isEmpty(m_kelei)){
                url.append("/m_kelei/"+m_kelei);
            }
            if(!StringUtils.isEmpty(m_batch_id)){
                url.append("/m_batch_id/"+m_batch_id);
            }
            if(!StringUtils.isEmpty(m_batch)){
                url.append("/m_batch/"+m_batch);
            }
            if(!StringUtils.isEmpty(m_province_id)){
                url.append("/m_province_id/"+m_province_id);
            }
            if(!StringUtils.isEmpty(m_province)){
                url.append("/m_province/"+m_province);
            }
            if(!StringUtils.isEmpty(m_specialty_id)){
                url.append("/m_specialty_id/"+m_specialty_id);
            }
            if(!StringUtils.isEmpty(m_specialty_name)){
                url.append("/m_specialty_name/"+m_specialty_name);
            }
            if(!StringUtils.isEmpty(m_favorites_by_university_codes)){
                url.append("/m_favorites_by_university_codes/"+m_favorites_by_university_codes);
            }

            String result = HttpRequestUtil.doGet(url.toString());

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }

            returnStr.append(obj.toJSONString().replace("\\", ""));

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }
        return returnStr.toString();
    }

    /**
     * 获取志愿指导院校
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/report",method = RequestMethod.GET)
    @ResponseBody
    public String report(@RequestParam(value="params",required=false) String params,
                         @RequestParam(value="type",required=false) Integer type,
                         @RequestParam(value="year",required=false) Integer year,
                         @RequestParam(value="batch",required=false) String batch) throws Exception{

        if(StringUtils.isEmpty(params)){
            throw new BizException(ERRORCODE.PARAM_ERROR.getCode(),ERRORCODE.PARAM_ERROR.getMessage());
        }

        UserAccountPojo userAccountPojo = getUserAccountPojo();

        if(userAccountPojo==null){
            throw new BizException(ERRORCODE.NO_LOGIN.getCode(),ERRORCODE.NO_LOGIN.getMessage());
        }

        Integer vipStatus = userAccountPojo.getVipStatus();

        if(vipStatus==null||vipStatus==0){
            throw new BizException(ERRORCODE.NOT_IS_VIP_ERROR.getCode(),ERRORCODE.NOT_IS_VIP_ERROR.getMessage());
        }

        JSONObject resultObj = new JSONObject();

        try {


            String result = HttpRequestUtil.httpPost("http://gx.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetReport_TEST",params,false);

            if(StringUtils.isEmpty(result)){
                throw new BizException(ERRORCODE.NO_RECORD.getCode(),ERRORCODE.NO_RECORD.getMessage());
            }

            JSONObject obj = JSON.parseObject(result);

            if("500".equals(obj.getString("code"))){
                throw new BizException(ERRORCODE.FAIL.getCode(),obj.getString("info"));
            }

            resultObj.put("report", obj);

        } catch (Exception e) {
            throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
        }

        JSONObject obj = JSON.parseObject(params);

        JSONArray data = obj.getJSONArray("data");

        List<String> codes = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            codes.add(data.getJSONObject(i).getString("m_university_code"));
        }

        long areaId= getAreaId();
        List<UniversityDetailDto> universityDetailDtos =universityExService.getUniversityDetailByCodes(codes,batch,type,year,areaId);

        JSONArray enrollArray = new JSONArray();

        JSONObject enrollObj = null;
        for(UniversityDetailDto universityDetailDto :universityDetailDtos){

            enrollObj = new JSONObject();

            int enrollNum = universityDetailDto.getEnrollNum();

            int planNum = universityDetailDto.getPlanNum();

            int num = enrollNum-planNum;

            if(num>0){
                universityDetailDto.setEnrollIntro("实际招生超过计划招生数，该院校增加计划几率大");
            }else if(num==0){
                universityDetailDto.setEnrollIntro("实际招生和计划招生数相等，该院校计划调整机会小");
            }else{
                universityDetailDto.setEnrollIntro("计划招生超过实际招生数，该院校报考热度低");
            }

            enrollObj.put("code",universityDetailDto.getId());
            enrollObj.put("name",universityDetailDto.getName());
            enrollObj.put("enrollIntro",universityDetailDto.getEnrollIntro());

            enrollArray.add(enrollObj);

        }

        resultObj.put("enroll",enrollArray);
        return JSON.toJSONString(resultObj);
    }

//
//    public static void main(String[] args) {
//        String params ="{\"data\":[{\"sequence\":1,\"m_university_code\":\"1207\",\"m_university_name\":\"天津农学院\"},{\"sequence\":2,\"m_university_code\":\"1207\",\"m_university_name\":\"天津农学院\"},{\"sequence\":3,\"m_university_code\":\"1302\",\"m_university_name\":\"天津职业大学\"},{\"sequence\":4,\"m_university_code\":\"1548\",\"m_university_name\":\"沧州医学高等专科学校\"}],\"related\":{\"m_batch_id\":\"4\",\"m_batch\":\"高职（专科）\"}}";
//
//        String result = null;
//        try {
//            result = HttpRequestUtil.httpPost("http://sn.gaokao360.gkzy114.com/index.php?s=/Restful/Guide/GetReport", params, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(result);
//    }

}

