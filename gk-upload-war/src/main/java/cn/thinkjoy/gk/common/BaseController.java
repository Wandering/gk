package cn.thinkjoy.gk.common;


import java.io.PrintWriter;

public class BaseController extends BaseCommonController{



    protected void outputResult(String text) throws Exception{
        response.setContentType("text");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            out.println(text);
        } catch (Exception e){
            throw e;
        } finally{
            if(null!=out){
                out.flush();
                out.close();
            }
        }
    }

}