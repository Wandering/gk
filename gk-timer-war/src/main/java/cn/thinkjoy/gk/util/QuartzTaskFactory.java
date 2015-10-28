package cn.thinkjoy.gk.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class QuartzTaskFactory implements Job {
	
	public synchronized void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			System.out.println("任务运行...");
//			TimerJob timerJob = (TimerJob) context.getMergedJobDataMap().get("timerJob");
//			System.out.println("任务名称: [" + timerJob.getName() + "]");
//
//			// 在这里执行你的任务...
//			try{
//				TaskUtils.invokMethod(timerJob);
//				TimerJobUtil.updateTimerJob(timerJob.getId().toString());
//			}catch(Exception e){
//				TimerJobUtil.updateExceptionTimerJob(timerJob.getId().toString(),context.getTrigger().getKey().getGroup(),e.getMessage());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
