define(function (require) {
    var $ = require('$');
    require('swiper');
    require('backToTop');
    require('getTime');


    var UI = {
        $listMsgItem: $('#list-msg-item'),
        $nextPage: $('#nextPage')
    };

    var localhosts = 'http://www.gkzy114.com';

    var searchValUrl = window.location.search;
    var num = searchValUrl.indexOf("?");
    var searchVal = searchValUrl.substr(num+16);
    $('#searchVal').val(searchVal);

    // 搜索
    $('#search-btn').on('click',function(){
       window.location.href='/before/exam.jsp?examSearchName='+ $('#searchVal').val()
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

    // 分页数据
    var pageSize = 8;
    var searchVals = $('#searchVal').val();
    console.log(searchVals)
    function getList(pageNo, pageSize,subjectId,years,searchVals) {
        // 获取首页列表
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
                console.log(result);
                if (result.rtnCode == "0800001") {
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
                        var listMsgHtml = ''
                            +'<li class="item">'
                            +'<a target="_blank" href="'+ localhosts + resources +'">'
                            +'<span class="subject-n"><strong>'+ subjectTypeTxt +'</strong></span>'
                            +'<span class="subject-t">'+ paperName +'</span>'
                            +'<span class="subject-d">上传时间：'+ getTime(createDate) +'</span>'
                            +'</a>'
                            +'</li>';

                        UI.$listMsgItem.append(listMsgHtml);
                    }
                    pageNo++;
                    UI.$listMsgItem.attr('pageNo', pageNo);
                    if (dataJson.length < pageSize) {
                        UI.$nextPage.removeClass('btn-primary').text("没有更多消息了")
                    }
                }
            });
    }
    // 初始化数据
    UI.$nextPage.on('click', function () {
        var pageNo = UI.$listMsgItem.attr('pageNo');
        getList(pageNo, pageSize,"",searchVals);
    }).click();


    // 科目筛选
    $(".subjectList").change(function(){
        var subjectId = $(this).find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,subjectId,searchVal);
    });



    // 年份选择
    $(".years-fun").change(function(){
        var years = $(this).find("option:selected").attr('value');
        var subjectId = $('.subjectList').find("option:selected").attr('value');
        UI.$listMsgItem.attr('pageNo',0);
        var pageNo = UI.$listMsgItem.attr('pageNo');
        UI.$listMsgItem.html('');
        getList(pageNo, pageSize,sortType,subjectId,searchVal);
    });


});
