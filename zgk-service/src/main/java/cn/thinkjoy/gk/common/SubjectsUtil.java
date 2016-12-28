package cn.thinkjoy.gk.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by admin on 2016/6/17.
 */
public class SubjectsUtil {

    public static int EVALUATION_COUNT_0 = 0;
    public static int EVALUATION_COUNT_1 = 1;
    public static int EVALUATION_COUNT_2 = 2;
    public static String genSubjects(String subjects){
        String[] tsubjects=subjects.split(" ");
        StringBuffer buffer = new StringBuffer();
        for(String str:tsubjects){
            buffer.append(str).append(" ");
        }
        if(buffer.length()>0){
            buffer.delete(buffer.length(),buffer.length()-1);
        }
        return buffer.toString();
    }

}
