<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>智高考专家后台</title>
        <%@ include file="../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management/user-info.css" />
    </head>
    <body>
        <div class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="main-title">
                            <h3><span class="user-name"></span>基本信息</h3>
                        </div>
                        <div class="user-base-info">
                            <img id="user-img" src="" />
                            <span class="sex">性别: <span id="user-sex"></span></span>
                            <span class="type">科类: <span id="user-type"></span></span>
                            <span class="grade">所在年级: <span id="user-grade"></span></span>
                            <span class="school">所在学校: <span id="user-school"></span></span>
                            <div class="question-describe">
                                <div class="title">问题描述: </div>
                                <div class="content" id="questionDesc"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="main-title">
                            <h3>测评报告</h3>
                        </div>
                        <table class="report-table" cellpadding="0" cellspacing="0">
                            <thead>
                                <tr>
                                    <th width="50%">评测名称</th>
                                    <th>最新评测时间</th>
                                </tr>
                            </thead>
                            <tbody id="report-list">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/info-management/user-info.js"></script>
    </body>
</html>
