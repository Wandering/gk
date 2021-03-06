package cn.thinkjoy.gk.controller.apesk;


import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.annotation.VipMethonTag;
import cn.thinkjoy.gk.common.BaseCommonController;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.pojo.UserAccountPojo;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.zgk.common.StringUtil;
import cn.thinkjoy.zgk.domain.ZgkApesk;
import cn.thinkjoy.zgk.domain.ZgkApeskCourse;
import cn.thinkjoy.zgk.dto.ZgkApeskDTO;
import cn.thinkjoy.zgk.remote.IZgkApeskCourseService;
import cn.thinkjoy.zgk.remote.IZgkApeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import cn.thinkjoy.zgk.dto.ZgkApeskDTO;

/**
 * @author huangshengqing
 * 2015年9月25日
 */
@Controller
@Scope(SpringMVCConst.SCOPE)
@RequestMapping(value = "/apesk")
public class ApeskController extends BaseCommonController {
    private String apeskUrl="http://www.apesk.com/h/go_zy_dingzhi.asp?checkcode=%s&hruserid=%s&l=%s&test_name=%s&test_email=%s";
	private static final Logger LOGGER= LoggerFactory.getLogger(ApeskController.class);
    @Autowired
    private IZgkApeskService zgkApeskService;
    @Autowired
    private IZgkApeskCourseService zgkApeskCourseService;
	private static final String  APESK_CHECKCODE = "O1I194H5LAHLJR2DIJ";
	private static final String  APESK_HRUSERID = "13726081881";
	private static final Integer STATE = 0;

	@ResponseBody
	@RequestMapping(value = "/queryApeskResult.do",method = RequestMethod.GET)
	public Map<String,Object> getApeskResult() {
		UserAccountPojo userAccountPojo = getUserAccountPojo();
		if (userAccountPojo == null) {
			throw new BizException(ERRORCODE.NO_LOGIN.getCode(), ERRORCODE.NO_LOGIN.getMessage());
		}

		Map map = new HashMap();
		map.put("userId", userAccountPojo.getId());
		map.put("userArea",this.getAreaId());
		map.put("state",STATE);
		List<ZgkApeskDTO> zgkApeskDTOList = zgkApeskService.selectUserApeskResult(map);
		Map resultMap = new HashMap();
		resultMap.put("apeskObj", zgkApeskDTOList);
		return resultMap;
	}

	/**查询测试列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryApeskTest.do", method = RequestMethod.GET)
	public List<ZgkApeskCourse> queryApeskTest(){
		int state = 0;
		String batch = "1";
		int start = -1;
		int size = -1;
		return zgkApeskCourseService.query(state,batch, start, size);
	}

	/**才储回调地址
	 * @param report_id
	 * @param liangbiao
	 * @param test_email
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value = "/returnurl.do", method = RequestMethod.GET)
	public ModelAndView returnurl(@RequestParam(value = "report_id") String report_id,
								  @RequestParam(value = "liangbiao")String liangbiao,
								  @RequestParam(value = "test_email")String test_email){
		HashMap<String, Object>map=new HashMap<String, Object>();
		map.put("report_id", report_id);
		map.put("liangbiao", liangbiao);
		map.put("test_email", test_email);
		if(!StringUtil.isNulOrBlank(test_email)&&!StringUtil.isNulOrBlank(liangbiao)&&test_email.contains("_")){
			String[] values =  test_email.split("_");
			int userId=Integer.parseInt(values[2]);
//			List<ZgkApesk> apList=zgkApeskService.query(Long.parseLong(userId+""),-1, liangbiao,test_email);
			Map callMap=new HashMap();
			callMap.put("liangBiao",liangbiao);
			callMap.put("userId",userId);
			callMap.put("testEmail",test_email);
			//这里判断test_email有没有第四个值  如果有说明是sass调取的
			Integer state = STATE;
			try {
				if (values.length>3){
					state=Integer.parseInt(values[3]);
				}
			}catch (Exception e){
				//吃掉这个异常
			}
			callMap.put("state",state);
			List<ZgkApesk> apList=zgkApeskService.selectApeskCallBack(callMap);
			if(apList!=null&&apList.size()>0){
				ZgkApesk apesk=apList.get(0);
//				if(apList.size()>1){
//					for(ZgkApesk ap:apList){
//						if(ap.getLiangBiao().equals(liangbiao)&&ap.getTestEmail().equals(test_email)&&userId==ap.getUserId()){
//							apesk=ap;
//							break;
//						}
//					}
//				}
				apesk.setReportDate(new Date());
				apesk.setReportId(Integer.parseInt(report_id));
				zgkApeskService.updateByPrimaryKeySelective(apesk);
				try {
					ZgkApeskCourse apeskCourse=zgkApeskCourseService.queryByLiangBiao(liangbiao);
					String reportUrl=apeskCourse.getReportUrl()+apesk.getReportId();
					return new ModelAndView("redirect:"+reportUrl);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}


	/**获取报告地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryReportUrl.do")
	public Map<String, Object>  queryReportUrl(@RequestParam(value = "liangbiao")String liangbiao,
											   @RequestParam(value = "acId")Integer acId){
		Map<String, Object> returnJsonData=new HashMap<>();
		UserAccountPojo userAccountPojo = getUserAccountPojo();
		if (userAccountPojo != null) {

			List<ZgkApesk> apList=zgkApeskService.query(userAccountPojo.getId(),acId, liangbiao,"",STATE);
			if(apList!=null&&apList.size()>0){
				ZgkApesk apesk=apList.get(0);
				ZgkApeskCourse apeskCourse=zgkApeskCourseService.queryByLiangBiao(liangbiao);
				returnJsonData.put("data", apeskCourse.getReportUrl()+apesk.getReportId());
			}
		} else {
			throw new BizException("error", "用户不存在!");
		}
		return returnJsonData;
	}
	/**获取报告地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryReportUrlById.do")
	public Map<String, Object>  queryReportUrl(@RequestParam(value = "id")Integer id){
		Map<String, Object> returnJsonData=new HashMap<>();
		UserAccountPojo userAccountPojo = getUserAccountPojo();
		if (userAccountPojo != null) {
			ZgkApesk apesk = zgkApeskService.selectByPrimaryKey(id);
			ZgkApeskCourse apeskCourse=zgkApeskCourseService.queryByLiangBiao(apesk.getLiangBiao());
			if (apesk.getReportId()!=null) {
				returnJsonData.put("data", apeskCourse.getReportUrl() + apesk.getReportId());
			}else {
				returnJsonData.put("data", false);
			}
		} else {
			throw new BizException("error", "用户不存在!");
		}
		return returnJsonData;
	}

	/**获取才储测试地址
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryApeskUrl.do")
	@VipMethonTag
	public Map<String, Object> queryApeskUrl(@RequestParam(value = "acId")Integer acId){
		UserAccountPojo userAccountPojo = getUserAccountPojo();
		Map<String, Object> returnJsonData= new HashMap<>();
		if (userAccountPojo != null) {
			//判断是否会员
			if(userAccountPojo.getVipStatus()==1){
				ZgkApeskCourse apeskCourse=zgkApeskCourseService.selectByPrimaryKey(acId);
				if(apeskCourse!=null){
					String liangbiao=apeskCourse.getLiangBiao();
					//testEmail 由批次+_+用户名+_+用户id
					String testName= userAccountPojo.getAccount();
					String testEmail=apeskCourse.getBatch()+"_"+testName+"_"+ userAccountPojo.getId();
//					List<ZgkApesk> apList= zgkApeskService.query(userAccountPojo.getId() ,acId, liangbiao,testEmail);
					Map map=new HashMap();
					map.put("userId",userAccountPojo.getId());
					map.put("acId",acId);
					map.put("liangBiao",liangbiao);
					map.put("testEmail",testEmail);
					List<ZgkApesk> apList= zgkApeskService.selectApeskLimit(map);
					if(apList==null||apList.size()<2) {//小于2条记录，开始做题
						ZgkApesk apesk = new ZgkApesk();
						apesk.setUserId(userAccountPojo.getId());
						apesk.setLiangBiao(liangbiao);
						apesk.setTestEmail(testEmail);
						apesk.setCreateDate(new Date());
						apesk.setState(STATE);
						apesk.setAcId(Long.parseLong(acId + ""));
						apesk = zgkApeskService.insertSelective(apesk);
						setData(testName, returnJsonData, liangbiao, testEmail);
						returnJsonData.put("id",apesk.getId());
					}else {//有记录的查看报表
						ZgkApesk apesk = apList.get(0);
						if (apesk.getReportId() == null) {
							setData(testName, returnJsonData, liangbiao, testEmail);
						} else {
							returnJsonData.put("data", apeskCourse.getReportUrl() + apesk.getReportId());
						}
					}
				}else{
					throw new BizException("error", "所选测试不存在!");
				}
			}else{
				throw new BizException("error", "只允许VIP会员参与!");
			}
		} else {
			throw new BizException("error", "用户不存在!");
		}
		return returnJsonData;
	}

	private void setData(String testName, Map<String, Object> returnJsonData, String liangbiao, String testEmail) {
		try {
            testName= URLEncoder.encode(testName, "gb2312");
            String url=String.format(apeskUrl, APESK_CHECKCODE,APESK_HRUSERID,liangbiao,testName,testEmail);
            returnJsonData.put("data",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		String testName="测试1号";
		HashMap<String, Object>map=new HashMap<String, Object>();
		try {
			testName=URLEncoder.encode(testName, "gb2312");

			String url=String.format(apeskUrl, APESK_CHECKCODE, APESK_HRUSERID,"",testName,"apesk_123");

			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/web/html/testApesk",map);
	}
}
