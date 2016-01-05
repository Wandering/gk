package cn.thinkjoy.gk.common;

/**
 * Created by admin on 2016/1/5.
 */
public class GkControllerUtil {
    public static String setDefault(String str,String defaultStr){
        if(str==null || "".equals(str)){
            str=defaultStr;
        }
        return str;
    }

    public static Integer setDefault(Integer num,Integer defaultNum){
        if(num==null){
            num=defaultNum;
        }
        return num;
    }

}
