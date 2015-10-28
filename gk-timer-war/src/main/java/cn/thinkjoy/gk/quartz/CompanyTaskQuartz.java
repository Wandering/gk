package cn.thinkjoy.gk.quartz;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.gk.protocol.ERRORCODE;

import java.util.ArrayList;
import java.util.List;

//MethodInvokingJob异步
public class CompanyTaskQuartz{
	
	public void executeInternal() throws Exception {
//		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		try{

//			TimerJobUtil.updateTimerJob(jobDataMap.getString("id"));
		} catch(Exception e){
//			try {
//				TimerJobUtil.updateExceptionTimerJob(jobDataMap.getString("id"),context.getTrigger().getKey().getGroup(),e.getMessage());
//			} catch (Exception e1) {
//				new YuleException(e1);
//			}
			throw new BizException(ERRORCODE.FAIL.getCode(), ERRORCODE.FAIL.getMessage());
		} finally{
			System.gc();
		}
	}
	
}
