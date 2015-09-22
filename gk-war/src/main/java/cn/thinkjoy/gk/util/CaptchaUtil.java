package cn.thinkjoy.gk.util;


import cn.thinkjoy.gk.constant.CaptchaConst;

import java.util.Random;

public class CaptchaUtil {
    
    /**
     * 生成随机字符串
     */
    public static String getRandomString(int randomLength){
    	 Random random = new Random();
        StringBuffer randomString = new StringBuffer("");
        String rand = null;
        for(int i=1;i<=randomLength;i++){
            rand = String.valueOf(CaptchaConst.RAND_STRING.charAt(random.nextInt(CaptchaConst.RAND_STRING.length()-1)));
            randomString.append(rand);
        }
        return randomString.toString();
    }
    
    /**
     * 生成随机数字字符串
     */
    public static String getRandomNumString(int randomLength){
    	 Random random = new Random();
        StringBuffer randomString = new StringBuffer("");
        String rand = null;
        for(int i=1;i<=randomLength;i++){
            rand = String.valueOf(CaptchaConst.RAND_NUM_STRING.charAt(random.nextInt(CaptchaConst.RAND_NUM_STRING.length()-1)));
            randomString.append(rand);
        }
        return randomString.toString();
    }
}