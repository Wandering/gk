package cn.thinkjoy.gk.common;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yangyongping on 2016/12/23.
 */
public class VipTimeUtil {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd");

    private static final String vipTime = "09-01";

    public static String getLastActiveDate(Long time){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        if (getVipTag()) {
            c.add(Calendar.YEAR, 2);
        }else {
            c.add(Calendar.YEAR, 3);
        }
        c.set(Calendar.MONTH, 8);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);

        return format.format(c.getTime());
    }

    public static Long getCardTimeOut(Long time) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(getLastActiveDate(time)));
        return c.getTimeInMillis();
    }

    public static boolean getVipTag(){
        return formatDate.format(new Date(System.currentTimeMillis())).compareTo(vipTime)<0;
    }
    public static void main(String[] args) throws ParseException {

        System.out.println(getCardTimeOut(System.currentTimeMillis()));
//        System.out.println(getVipTag());
//        System.out.println(getLastActiveDate(System.currentTimeMillis()));
    }
}
