<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="../common/meta.jsp" %>
</head>
<body>

<div id="examPlaySwf" style="width:1000px;margin:0 auto">
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="1000" height="1024">
    <param name="movie" id="examPlayUrl" value="" />
    <param name="quality" value="high" />
    <embed id="examPlayUrl2" src="" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="1000" height="1024"></embed>
</object>
</div>



<script>
    seajs.use("/static/src/before/scripts/exam-play");
</script>
</body>
</html>
