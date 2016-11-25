<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>智高考专家后台</title>
        <%@ include file="../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management/report-info.css" />
    </head>
    <body>
        <div class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="reportChart" style="width: 100%;height: 400px;margin: 40px 0;"></div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/info-management/report-info.js"></script>
    </body>
</html>
