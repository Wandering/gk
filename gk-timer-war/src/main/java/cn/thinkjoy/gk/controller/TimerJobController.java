package cn.thinkjoy.gk.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.common.BaseController;
import cn.thinkjoy.gk.constant.PageConst;
import cn.thinkjoy.gk.constant.SpringMVCConst;
import cn.thinkjoy.gk.constant.TimerJobConst;
import cn.thinkjoy.gk.domain.Timerjob;
import cn.thinkjoy.gk.protocol.DateStyle;
import cn.thinkjoy.gk.protocol.ERRORCODE;
import cn.thinkjoy.gk.protocol.Page;
import cn.thinkjoy.gk.service.ITimerjobService;
import cn.thinkjoy.gk.util.DateUtil;
import cn.thinkjoy.gk.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(SpringMVCConst.SCOPE)
public class TimerJobController extends BaseController{
	
	@Autowired
	private ITimerjobService timerjobService;

	/**
	 * 查询定时任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findTimerJob",method = RequestMethod.GET)
	public String findTimerJobList(@RequestParam(value="pageNo",required=false)Integer pageNo) throws Exception {
		if(pageNo==null||pageNo<=0){
			pageNo = 1;
		}
		PaginationUtil paginationUtil = new PaginationUtil();
		Map<String,Object> params = new HashMap<>();
		Page<Timerjob> page = new Page<>();
		try {
			List<Timerjob> lists = timerjobService.queryPage(params, PageConst.PAGE_SIZE_TEN,pageNo);
			page.setDatas(lists);
			int count = timerjobService.count(params);
			page.setPageCount(count);
			StringBuffer htmls = new StringBuffer("");
			htmls.append("<tfoot>");
			htmls.append("<tr>");
			htmls.append("<td colspan=\"11\">");
			htmls.append("<div class=\"bulk-actions align-left\">");
//			htmls.append("<a class=\"button\" href=\"#\">批量删除</a>");
			htmls.append("</div>");
			htmls.append(paginationUtil.getPaginationHtml(page));
			htmls.append("<div class=\"clear\"></div>");
			htmls.append("</td>");
			htmls.append("</tr>");
			htmls.append("</tfoot>");
			if(null!=lists&&lists.size()>0){
				for(Timerjob timerJob:lists){
					htmls.append("<tbody>");
					htmls.append("<tr>");
					htmls.append("<td>"+timerJob.getName()+"</td>");
					htmls.append("<td>"+timerJob.getGroup()+"</td>");
					htmls.append("<td>"+timerJob.getCronExpression()+"</td>");
					htmls.append("<td>"+ TimerJobConst.ASYNCS[timerJob.getAsync()]+"</td>");
					htmls.append("<td>"+TimerJobConst.STATUS[timerJob.getStatus()]+"</td>");
					htmls.append("<td>"+timerJob.getExecuteNum()+"</td>");
					htmls.append("<td>"+ DateUtil.DateToString(new Date(timerJob.getExecuteTime()), DateStyle.YYYY_MM_DD_HH_MM_SS_EN)+"</td>");
					htmls.append("<td>"+timerJob.getErrorMessage()+"</td>");
					htmls.append("<td>");
					htmls.append("<a class=\"button\" href=\"/findTimerJobById.do?id="+timerJob.getId()+"\" >更新</a>&nbsp;");
					htmls.append("<a class=\"button\" href=\"/deleteTimerJob.do?id="+timerJob.getId()+"\" >删除</a>");
					htmls.append("</td>");
					htmls.append("</tr>");
					htmls.append("</tbody>");
				}
				page.getDatas().clear();
			}else{
				htmls.append("<tbody>");
				htmls.append("<tr>");
				htmls.append("<td style=\"text-align: center;\" colspan=\"10\">暂无任务</td>");
				htmls.append("</td>");
				htmls.append("</tr>");
				htmls.append("</tbody>");
			}
			request.setAttribute("timerJobHtmls", htmls);
		} catch (Exception e) {
			throw new BizException(ERRORCODE.FAIL.getCode(),ERRORCODE.FAIL.getMessage());
		}finally{
			System.gc();
		}
		return "index";
	}
//
//	/**
//	 * 添加定时任务
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/insertTimerJob",method = RequestMethod.POST)
//	public String insertTimerJob(TimerJob timerJob) throws Exception {
//		try {
//			String superName = Class.forName(timerJob.getClassName()).getSuperclass().getSimpleName();
//			if(TimerJobConst.METHOD_INVOKING_JOB.equals(superName)){
//				timerJob.setAsync(TimerJobConst.ASYNC_ONE);
//			}else if(TimerJobConst.SATEFUL_METHOD_INVOKING_JOB.equals(superName)){
//				timerJob.setAsync(TimerJobConst.ASYNC_ZERO);
//			}
//			timerJobMongoImpl.insertTimerJob(timerJob);
//			JobDataMap paramsMap = new JobDataMap();
//			paramsMap.put("id",timerJob.getId().toString());
//			QuartzUtil.enableCronSchedule(timerJob, paramsMap);
//		} catch (Exception e) {
//			new YuleException(e);
//			throw e;
//		}finally{
//			System.gc();
//		}
//		return ActionReturnConst.REDIRECT+"/findTimerJob.do";
//	}
//
//	/**
//	 * 删除定时任务
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/deleteTimerJob",method = RequestMethod.GET)
//	public String deleteTimerJob(@RequestParam(value="id",required=false)String id) throws Exception {
//		try {
//			TimerJob timerJob = timerJobMongoImpl.findTimerJobById(id);
//			boolean flag = QuartzUtil.disableSchedule(timerJob.getId().toString(),timerJob.getGroup());
//			if(flag){
//				timerJobMongoImpl.deleteTimerJob(id);
//			}
//		} catch (Exception e) {
//			new YuleException(e);
//			throw e;
//		}
//		return ActionReturnConst.REDIRECT+"/findTimerJob.do";
//	}
//
//	/**
//	 * 根据ID查询任务
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/findTimerJobById",method = RequestMethod.GET)
//	public String findTimerJobById(@RequestParam(value="id",required=false)String id) throws Exception {
//		try {
//			TimerJob timerJob = timerJobMongoImpl.findTimerJobById(id);
//			request.setAttribute("timerJob",timerJob);
//		} catch (Exception e) {
//			new YuleException(e);
//			throw e;
//		} finally{
//			System.gc();
//		}
//		return "update";
//	}
//
//	/**
//	 * 更新定时任务
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/updateTimerJob",method = RequestMethod.POST)
//	public String updateTimerJob(TimerJob timerJob) throws Exception {
//		try {
//			String id = timerJob.getId().toString();
//			TimerJob job = timerJobMongoImpl.findTimerJobById(id);
////			String superName = Class.forName(timerJob.getClassName()).getSuperclass().getSimpleName();
////			if(TimerJobConst.METHOD_INVOKING_JOB.equals(superName)){
////				timerJob.setAsync(TimerJobConst.IS_ASYNC_ONE);
////			}else if(TimerJobConst.SATEFUL_METHOD_INVOKING_JOB.equals(superName)){
////				timerJob.setAsync(TimerJobConst.IS_ASYNC_ZERO);
////			}
//			if(TimerJobConst.STATUS_FALSE==timerJob.getStatus()){
//				QuartzUtil.disableSchedule(id, job.getGroup());
//			}else{
//				job.setCronExpression(timerJob.getCronExpression());
//				JobDataMap jobDateMap = new JobDataMap();
//				jobDateMap.put("id", id);
//				QuartzUtil.enableCronSchedule(job, jobDateMap);
//			}
//			timerJobMongoImpl.updateTimerJob(timerJob);
//		} catch (Exception e) {
//			new YuleException(e);
//			throw e;
//		}
//		return ActionReturnConst.REDIRECT+"/findTimerJob.do";
//	}
//
}
