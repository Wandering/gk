package cn.thinkjoy.gk.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yangyongping on 2016/12/23.
 */
public class VipTimeUtil {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String getLastActiveDate(Long time){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.add(Calendar.YEAR, 3);
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);

        return format.format(c.getTime());
    }
}
