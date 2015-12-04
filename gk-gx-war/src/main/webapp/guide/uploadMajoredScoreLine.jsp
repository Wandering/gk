<%--
  Created by IntelliJ IDEA.
  User: zuohao
  Date: 15/12/4
  Time: 下午2:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="testController/uploadMajoredScoreLine.do" method="post" enctype="multipart/form-data">
    <input id="uploadFile" type="file" name="uploadFile"
           class="required"/><br/>
    <input type="submit" value="上传">
  </form>
</body>
</html>
