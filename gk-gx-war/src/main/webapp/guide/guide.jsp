<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>政策解读</title>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="http://cdn.gaokao360.net/static/global/guide/styles/guide.min.css" />
</head>
<body>
<%@ include file="/common/header.jsp"%>

<div class="content">
    <div class="w1000">
        <div class="content-title mt60">
            <p class="fl w40"></p>
            <p class="fl w20">
                <i class="fl arraw"></i>
                <span>政策解读</span>
                <i class="fr arraw"></i>
            </p>
            <p class="fl w40"></p>
        </div>
        <h6 class="w1000 ta sub-title c888 mt20">专业而通俗地解读广西高考招生文件、高考志愿填报和招生的相关政策。</h6>
        <p class="sub-article c888 mt60">
            广西招办每年公布的《广西壮族自治区普通高考方案》，是广西高考招生的纲领性文件，家长及考生都应深入了解。新一年的广西招生政策会陆续公布，请随时关注本平台各批次招生政策，在此我们会对此“方案”进行专业通俗的解读。以便于家长和考生正确把握。
        </p>

        <div id="tab_title_content">

        </div>

        <div class="tab-content" id="tab_content">
        </div>
    </div>
</div>

<script type="text/javascript">
    //    seajs.use("http://cdn.gaokao360.net/static/global/guide/scripts/guide");
    seajs.use("/static/src/guide/scripts/guide");
</script>
<%@ include file="/common/footer.jsp"%>
</body>
</html>
