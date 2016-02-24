<%--
  Created by IntelliJ IDEA.
  User: liusven
  Date: 16/2/24
  Time: 下午8:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.*,java.net.*" %>
<%!
    private String getIp(){
        String strTmp="";
        try{
            strTmp =InetAddress.getLocalHost().getHostAddress();
            return strTmp;
        }
        catch(Exception e){
            return strTmp;
        }
    }
%>
服务器IP:<%=getIp()%>