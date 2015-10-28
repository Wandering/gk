package cn.thinkjoy.gk.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.yule.mongo.pojo.TimerJob;

public class TaskUtils {
	
	 public final static Logger log = Logger.getLogger(TaskUtils.class);  
	 
	/** 
     * 通过反射调用scheduleJob中定义的方法 
     *  
     * @param scheduleJob 
     * 
     */  
    public static void invokMethod(TimerJob timerJob) {  
        Object object = null;  
        Class clazz = null;  
                //springId不为空先按springId查找bean  
//        if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {  ''
//            object = SpringUtils.getBean(scheduleJob.getSpringId());  
//        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {  
            try {  
                clazz = Class.forName(timerJob.getClassName());  
                object = clazz.newInstance();  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
  
//        }  
        if (object == null) {  
            log.error("任务名称 = [" + timerJob.getName() + "]---------------未启动成功，请检查是否配置正确！！！");  
            return;  
        }  
        clazz = object.getClass();  
        Method method = null;  
        try {  
            method = clazz.getDeclaredMethod("executeInternal");  
        } catch (NoSuchMethodException e) {  
            log.error("任务名称 = [" + timerJob.getName() + "]---------------未启动成功，方法名设置错误！！！");  
        } catch (SecurityException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        if (method != null) {  
            try {  
                method.invoke(object);  
            } catch (IllegalAccessException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
          
    }  
}
