define(function (require) {
    var $ = require('$');
    require('backToTop');


    var UI = {
        $listMsgItem: $('#list-msg-item'),
        $nextPage: $('#nextPage')
    };

    var localhosts = 'http://www.gkzy114.com';
    var detailsUrl = decodeURIComponent(window.location.search);
    var classifyType = detailsUrl.substr(14, 1);
    var num = detailsUrl.indexOf('&');
    var searchV = detailsUrl.substr(num + 9);
    $('#searchVal').val(searchV);

    // 搜索
    $('#search-btn').on('click',function(){
       window.location.href='/before/exam.jsp?classifyType=3&searchV='+ $('#searchVal').val()
    });



    // 获取科目
    function getSubjectList(){
        $.getJSON(
            '/before/video/getSubjectList.do',
            function (result) {
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    for (var i = 0; i < dataJson.length; i++) {
                        var tabLi = dataJson[i].subjectName,
                            tabId = dataJson[i].subjectId;
                        var subjectListHtml = '<option value="'+ tabId +'">'+ tabLi +'</option>';
                        $('.subjectList').append(subjectListHtml);
                    }
                }
            });
    }
    getSubjectList();

    // 获取年份
    function getYearsList(){
        $.ajax({
            url:'/vip/getAccount.do',
            type: 'GET',
            dataType: 'JSON',
            success: function (result) {
                var years = '';
                if(result.rtnCode=="0000000"){
                    var vipStatus = result.bizData.vipStatus;
                    if(vipStatus==0){
                        $('.years-fun').append('<option value="2014">2014年</option><option value="2013">2013年</option>')
                    }
                    if(vipStatus==1){
                        $('.years-fun').append('<option value="2015">2015年(vip用户专享)</option><option value="2014">2014年</option><option value="2013">2013年</option>')
                    }
                }
            }
        });
    }
    getYearsList();

    // 分页数据
    var pageSize = 8;
    var searchVals = $('#searchVal').val();
    function getList(pageNo, pageSize,subjectId,years,searchVals) {
        $.getJSON(
            "/before/paper/getPaperList.do",
            {
                pageNo: pageNo,
                pageSize: pageSize,
                sortType :1 ,
                years:years,
                subjectId:subjectId,
                searchName:searchVals
            },
            function (result) {
                console.log(result)
                if (result.rtnCode == "0800001" || result.rtnCode == "1000004") {
                    UI.$listMsgItem.append('<p class="noContent">' + result.msg + '</p>');
                    UI.$nextPage.hide();
                }
                if (result.rtnCode == "0000000") {
                    var dataJson = result.bizData;
                    if(dataJson.length>0){
                        UI.$nextPage.show();
                    }

                    for (var i = 0; i < dataJson.length; i++) {
                        var paperName = dataJson[i].paperName,
                            createDate = dataJson[i].createDate,
                            subjectType = dataJson[i].subjectId,
                            resources = dataJson[i].resources;
                        var  subjectTypeTxt = '';
                        switch (subjectType){
                            case 1:
                                subjectTypeTxt='语文';
                                break;
                            case 2:
                                subjectTypeTxt='数学';
                                break;
                            case 3:
                                subjectTypeTxt='英语';
                                break;
                            case 10:
                                subjectTypeTxt='理综';
                                break;
                            case 11:
                                subjectTypeTxt='文综';
                                break;
                            default :
                                subjectTypeTxt='其他';
                        }

                        var date = new Date(createDate).Format('yyyy-MM-dd');
                        var listMsgHtml = ''
                            +'<li class="item">'
                            +'<a target="_blank" href="/before/exam-play.jsp?url='+ localhosts + resources +'">'
                            +'<span class="subject-n"><strong>'+ subjectTypeTxt +'</strong></span>'
                            +'<span class="subject-t">'+ paperName +'</span>'
                            +'<span class="subject-d">上传时间：'+ date +'</span>'
                            +'</a>'
                            +'</li>';
                        UI.$listMsgItem.append(listMsgHtml);
                    }
                    pageNo++;
                    UI.$listMsgItem.attr('pageNo', pageNo);
                    if (dataJson.length < pageSize) {
                        UI.$nextPage.hide();
                    }
                }
            });
    }

    // 初始化数据
    UI.$nextPage.on('click', function () {
        var pageNo = UI.$listMsgItem.attr('pageNo');
        var selV = $('.subjectList').find('option:checked').val();
        console.log(selV);
        $.ajax({
            url:'/vip/getAccount.do',
            type: 'GET',
            dataType: 'JSON',
            success: function (result) {
                console.log(result);
                if (result.rtnCode == "1000004") {
                    UI.$listMsgItem.append('<p class="noContent">' + result.msg + '</p>');
                    UI.$nextPage.hide();
                }
                if(result.rtnCode=="0000000"){
                    var vipStatus = result.bizData.vipStatus;
                    if(vipStatus==0){
                        getList(pageNo, pageSize,selV,"2014",searchVals);
                    }
                    if(vipStatus==1){
                        getList(pageNo, pageSize,selV,"2015",searchVals);
                    }
                }
            }
        });
    }).click();


    // 科目筛选
    $(".subjectList").change(function(){
        var subjectId = $(this).find("option:selected").attr('value');
        var years = $('.years-fun').find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,subjectId,years,"");
        $('#searchVal').val("");
    });



    // 年份选择
    $(".years-fun").change(function(){
        var years = $(this).find("option:selected").attr('value');
        var subjectId = $('.subjectList').find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,subjectId,years,"");
        $('#searchVal').val("");
    });




});
