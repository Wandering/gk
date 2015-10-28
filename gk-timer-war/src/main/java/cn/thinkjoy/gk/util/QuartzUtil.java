package cn.thinkjoy.gk.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.yule.common.exception.YuleException;
import com.yule.constant.TimerJobConst;
import com.yule.mongo.pojo.TimerJob;

/**
 * 
 */
public class QuartzUtil {
	
	private static Scheduler scheduler;

	static {
		scheduler = (StdScheduler) new ClassPathXmlApplicationContext(TimerJobConst.QUARTZ_PATH).getBean("schedulerFactory");
	}

	/**
	 * 启动一个自定义的job
	 * @param timerJob 自定义的job
	 * @param paramsMap 传递给job执行的数据
	 * @return 成功则返回true，否则返回false
	 */
	public static boolean enableCronSchedule(TimerJob timerJob,
			JobDataMap paramsMap) {
		if (timerJob == null) {
			return false;
		}
		try {
			String taskId = TimerJobConst.TASK+timerJob.getId();
			String groupId = TimerJobConst.GROUP+timerJob.getGroup();
			TriggerKey triggerKey = TriggerKey.triggerKey(
					taskId, groupId);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				JobDetail jobDetail = null;
//				if (TimerJobConst.ASYNC_ZERO==timerJob.getAsync()) {
//					jobDetail = new JobDetail(taskId,groupId,
//							Class.forName(timerJob.getClassName()));
//					
//				} else {
//					jobDetail = new JobDetail(id,
//							timerJob.getGroup(),
//							Class.forName(timerJob.getClassName()));
//				}
				Class clazz = TimerJobConst.ASYNC_ZERO==timerJob.getAsync() ? QuartzTaskFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
				jobDetail = JobBuilder.newJob(clazz).withIdentity(taskId,groupId).build();
				paramsMap.put("timerJob", timerJob);
				jobDetail.getJobDataMap().putAll(paramsMap);
//				trigger = new CronTrigger(id+TimerJobConst.TRIGGER,
//						timerJob.getGroup(),
//						timerJob.getCronExpression());
//				scheduler.scheduleJob(jobDetail, trigger);
				// 表达式调度构建器
			    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(timerJob.getCronExpression());
			    //按新的表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(taskId,groupId).withSchedule(scheduleBuilder).build();
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// trigger已存在，则更新相应的定时设置
			    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(timerJob.getCronExpression());
			    // 按新的cronExpression表达式重新构建trigger
			    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			    // 按新的trigger重新设置job执行
			    scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 禁用一个job
	 * @param id 需要被禁用的job的id
	 * @param group 需要被警用的组ID
	 * @return 成功则返回true，否则返回false
	 */
	public static boolean disableSchedule(String taskId, String groupId) {
		if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(groupId)) {
			return false;
		}
		try {
			Trigger trigger = getJobTrigger(taskId, groupId);
			if (null != trigger) {
				JobKey jobKey = JobKey.jobKey(TimerJobConst.TASK+taskId,groupId);
				scheduler.deleteJob(jobKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 得到job的详细信息
	 * @param id job的id
	 * @param group job的组ID
	 * @return job的详细信息,如果job不存在则返回null
	 */
	public static JobDetail getJobDetail(String taskId ,String groupId) throws SchedulerException{
		if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(groupId)) {
			return null;
		}
		JobKey jobKey = JobKey.jobKey(TimerJobConst.TASK+taskId,groupId);
		try {
			return scheduler.getJobDetail(jobKey);
		} catch (SchedulerException e) {
			new YuleException("得到job的详细信息发生错误!",e);
			throw e;
		}
	}

	/**
	 * 得到job对应的Trigger
	 * @param id job的id
	 * @param group job的组ID
	 * @return job的Trigger,如果Trigger不存在则返回null
	 */
	public static Trigger getJobTrigger(String taskId, String groupId) throws SchedulerException{
		if (StringUtils.isEmpty(taskId) || StringUtils.isEmpty(groupId)) {
			return null;
		}
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(TimerJobConst.TASK+taskId,groupId);
			return scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			new YuleException("得到job对应的Trigger!",e);
			throw e;
		}
	}

}
