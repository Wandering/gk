package cn.thinkjoy.gk.service.impl.api;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.api.IApeskApi;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.zgk.domain.ZgkApesk;
import cn.thinkjoy.zgk.domain.ZgkApeskCourse;
import cn.thinkjoy.zgk.dto.ZgkApeskDTO;
import cn.thinkjoy.zgk.remote.IZgkApeskCourseService;
import cn.thinkjoy.zgk.remote.IZgkApeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongping on 2016/11/15.
 */
@Service("ApeskApiImpl")
public class ApeskApiImpl implements IApeskApi {
    private static final String APESK_CHECKCODE = "O1I194H5LAHLJR2DIJ";
    private static final String APESK_HRUSERID = "13726081881";
    private String apeskUrl = "http://www.apesk.com/h/go_zy_dingzhi.asp?checkcode=%s&hruserid=%s&l=%s&test_name=%s&test_email=%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApeskApiImpl.class);
    private static final Integer STATE = 1;

    @Autowired
    private IZgkApeskService zgkApeskService;
    @Autowired
    private IZgkApeskCourseService zgkApeskCourseService;

    /**
     * 获取才储接口
     */
    @Override
    public Map<String, Object> queryApeskUrl(Long userId, Long areaId, Integer acId, String key) {
        Map<String, Object> returnJsonData = new HashMap<>();
        //判断是否会员
        ZgkApeskCourse apeskCourse = zgkApeskCourseService.selectByPrimaryKey(acId);
        if (apeskCourse != null) {
            String liangbiao = apeskCourse.getLiangBiao();
            //testEmail 由批次+_+用户名+_+用户id
            String testName = key;
            String testEmail = apeskCourse.getBatch() + "_" + testName + "_" + userId + "_" + STATE;
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("acId", acId);
            map.put("liangBiao", liangbiao);
            map.put("testEmail", testEmail);
            map.put("state", STATE);
            List<ZgkApesk> apList = zgkApeskService.selectApeskLimit(map);
            if (apList==null || apList.size()==0) {
                ZgkApesk apesk = new ZgkApesk();
                apesk.setUserId(userId);
                apesk.setLiangBiao(liangbiao);
                apesk.setTestEmail(testEmail);
                apesk.setCreateDate(new Date());
                apesk.setState(STATE);
                apesk.setAcId(Long.parseLong(acId + ""));
                apesk = zgkApeskService.insertSelective(apesk);
                setData(testName, returnJsonData, liangbiao, testEmail);
                returnJsonData.put("id",apesk.getId());
            }else {//有记录的删除记录同时返回报表
                ZgkApesk apesk = apList.get(0);
                if (apesk.getReportId() == null) {
                    setData(testName, returnJsonData, liangbiao, testEmail);
                } else {
                    zgkApeskService.deleteByPrimaryKey(Integer.valueOf(apesk.getId()+""));
                    returnJsonData.put("data", apeskCourse.getReportUrl() + apesk.getReportId());
                }
            }
        }
        return returnJsonData;
    }


    /**
     * 获取报告地址
     *
     * @param liangbiao
     * @param acId
     * @return
     */
    @Override
    public Map<String, Object> queryReportUrl(String liangbiao, Integer acId, Long userId) {
        Map<String, Object> returnJsonData = new HashMap<>();
        List<ZgkApesk> apList = zgkApeskService.query(userId, acId, liangbiao, "",STATE);
        if (apList != null && apList.size() > 0) {
            ZgkApesk apesk = apList.get(0);
            ZgkApeskCourse apeskCourse = zgkApeskCourseService.queryByLiangBiao(liangbiao);
            returnJsonData.put("data", apeskCourse.getReportUrl() + apesk.getReportId());
        }
        return returnJsonData;
    }

    /**
     * 获取报告地址
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryReportUrl(Integer id) {
        Map<String, Object> returnJsonData=new HashMap<>();
            ZgkApesk apesk = zgkApeskService.selectByPrimaryKey(id);
            ZgkApeskCourse apeskCourse=zgkApeskCourseService.queryByLiangBiao(apesk.getLiangBiao());
            if (apesk.getReportId()!=null) {
                returnJsonData.put("data", apeskCourse.getReportUrl() + apesk.getReportId());
            }else {
                returnJsonData.put("data", false);
            }
            return returnJsonData;
    }

    /**
     * 获取测评列表
     *
     * @param areaId
     * @return
     */
    @Override
    public Map<String, Object> getApeskResult(Long userId,Long areaId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("userArea", areaId);
        map.put("state", STATE);
        List<ZgkApeskDTO> zgkApeskDTOList = zgkApeskService.selectUserApeskResult(map);
        Map resultMap = new HashMap();
        resultMap.put("apeskObj", zgkApeskDTOList);
        return resultMap;
    }

    private void setData(String testName, Map<String, Object> returnJsonData, String liangbiao, String testEmail) {
        try {
            testName = URLEncoder.encode(testName, "gb2312");
            LOGGER.info("testName=" + testName);
            String url = String.format(apeskUrl, APESK_CHECKCODE, APESK_HRUSERID, liangbiao, testName, testEmail);
            returnJsonData.put("data", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
