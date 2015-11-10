define(function (require) {
    var $ = require('$');


    $(function () {
        var UI = {
            $listMsgItem: $('#list-msg-item'),
            $nextPage: $('#nextPage')
        };
        var pageSize = 4;


        UI.$nextPage.on('click', function () {
            var pageNo = UI.$listMsgItem.attr('pageNo');
            getCollect(pageNo, pageSize);
        }).click();

        function getCollect(pageNo, pageSize) {
            $.get('/userCollection/getUserCollectPojoList.do?offset='+pageNo + "&rows="+pageSize, function (data) {
                var dataJson = data.bizData;
                if (data.rtnCode == "0000000") {
                    //if (dataJson.length > 0 && dataJson.length < (pageSize+1) ) {
                    //    UI.$nextPage.show();
                    //}else if(dataJson.length == 0){
                    //    UI.$listMsgItem.append('<tr><td colspan="6"><p class="noContent-tips">暂无数据</p></td></tr>')
                    //}
                    console.log("dataJson.length:"+dataJson.length)
                    if(dataJson.length > 0){
                        UI.$nextPage.show();
                        for (var i = 0; i < dataJson.length; i++) {
                            console.log(dataJson);
                            var universityName = dataJson[i].universityName
                                ,provinceName = dataJson[i].provinceName
                                ,universityType = dataJson[i].universityType
                                ,subjection = dataJson[i].subjection
                                ,universityId = dataJson[i].universityId
                                ,propertyName = dataJson[i].propertyName;

                            var listMsgHtml = '<tr>'
                                + '<td><a target="_blank" href="/consult/school_detail.jsp?id='+ universityId +'">'+ universityName +'</a></td>'
                                + '<td>'+ provinceName +'</td>'
                                + '<td>'+ universityType +'</td>'
                                + '<td>'+ subjection +'</td>';
                            if(propertyName){
                                listMsgHtml+= '<td>'+ propertyName +'</td>'
                            }else{
                                listMsgHtml+= '<td>-</td>'
                            }
                            listMsgHtml+= '<td><a href="javascript:;" universityId="'+ universityId +'" class="cancel-collect"></a></td>';
                            + '</tr>';
                            UI.$listMsgItem.prepend(listMsgHtml);
                        }
                        pageNo = parseInt(pageNo) + 4 ;
                        console.log(pageNo)
                        UI.$listMsgItem.attr('pageNo', pageNo);
                    }
                    UI.$nextPage.hide();

                    //if (dataJson.length < pageSize) {
                    //    UI.$nextPage.hide();
                    //    //$('.noContent-tips').hide();
                    //}
                }

            });
        }

        // 取消收藏
         $('#collect-table').on('click','.cancel-collect',function(){
             var $this = $(this);
             var universityId = $this.attr('universityId');
             $.get('/userCollection/deleteUserCollect.do?universityId='+universityId,function(data){
                 console.log(data)
                 if(data.rtnCode == '0000000'){
                     if(data.bizData){
                         $this.parents('tr').remove();
                     }
                 }
             })
         })


    })

});
