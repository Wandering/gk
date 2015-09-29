define(function (require) {
    var $ = require('$');
    require('backToTop');


    var searchValUrl = decodeURIComponent(window.location.search);
    var classifyType = searchValUrl.substr(14, 1);
    //var num = searchValUrl.indexOf("?");
    //var scoreV  = searchValUrl.substr(num+8);
    //var batchV;
    //var subjectTypeV;
    console.log( classifyType)




    //$.ajax({
    //    url:'/before/collegeRecommend/getCollegeList.do',
    //    type: 'POST',
    //    dataType: 'JSON',
    //    data:{
    //        "m_aggregateScore":500,
    //        "m_batch":"二批本科",
    //        "m_kelei":"文史"
    //    },
    //    success: function (res) {
    //        console.log(res);
    //    }
    //});



});
