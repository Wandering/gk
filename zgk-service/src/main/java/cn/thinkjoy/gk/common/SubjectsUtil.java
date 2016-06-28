package cn.thinkjoy.gk.common;

import org.springframework.stereotype.Component;

/**
 * Created by admin on 2016/6/17.
 */
public class SubjectsUtil {
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
