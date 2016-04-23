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
    private String getIp() throws SocketException {
        Enumeration netInterfaces=NetworkInterface.getNetworkInterfaces();
        InetAddress ip=null;
        boolean findFlag = false;
        while(netInterfaces.hasMoreElements()){
            NetworkInterface ni=(NetworkInterface)netInterfaces.nextElement();
            Enumeration addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                ip= (InetAddress)addresses.nextElement();
                if(ip.getHostAddress().indexOf(":")==-1&&!"127.0.0.1".equals(ip.getHostAddress())){
                    findFlag = true;
                }
                else{
                    ip=null;
                }
            }
            if(findFlag)
            {
                break;
            }
        }
        if(ip == null)
        {
            return "";
        }
        return ip.getHostAddress();
    }

%>
服务器IP:<%=getIp()%>