define(function (require) {
    var $ = require('$');
    var pageErrorTip = require('pageErrorTip');
    var UI = {
        $listMsgItem: $('#list-msg-item'),
        $nextPage: $('#nextPage')
    };
    var pageSize = 8;

    $(function () {
        UI.$nextPage.on('click', function () {
            var pageNo = UI.$listMsgItem.attr('pageNo');
            //getCollect(pageNo, pageSize);
            Collect.getCollect(pageNo, pageSize);
        }).click();

        $('#collect-table').on('click','.cancel-collect',function(){
            var $this = $(this);
            var universityId = $this.attr('universityId');
            $.get('/userCollection/deleteUserCollect.do?universityId='+universityId,function(data){
                console.log(data)
                if(data.rtnCode == '0000000'){
                    UI.$listMsgItem.html('');
                    if(data.bizData){
                        $this.parents('tr').remove();
                        $('tr.noData').hide();
                        UI.$listMsgItem.attr('pageNo','0');
                        var pageNo = UI.$listMsgItem.attr('pageNo');
                        Collect.getCollect(pageNo, pageSize)
                        //console.log(UI.$listMsgItem.find('tr').length)
                        var trLen = UI.$listMsgItem.find('tr').length;
                        if(trLen < (pageSize+1)){
                            UI.$nextPage.hide();
                        }
                        //if(trLen == 0){
                        //    UI.$listMsgItem.append('<tr class="noData"><td colspan="6"><br/><br/>'+ pageErrorTip('暂无相关数据') +'<br/><br/></td></tr>')
                        //}
                    }
                }
            })
        })

        /*
        function getCollect(pageNo, pageSize) {
            $.get('/userCollection/getUserCollectPojoList.do?offset='+pageNo + "&rows="+pageSize, function (data) {
                var dataJson = data.bizData;
                if (data.rtnCode == "0000000") {
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
                        pageNo = parseInt(pageNo) + pageSize ;
                        UI.$listMsgItem.attr('pageNo', pageNo);
                    }else{
                        UI.$nextPage.hide();
                        UI.$listMsgItem.append('<tr class="noData"><td colspan="6"><p class="noContent-tips">没有收藏数据了</p></td></tr>')
                    }
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
                         $('tr.noData').hide();
                         console.log(UI.$listMsgItem.find('tr').length)
                         var trLen = UI.$listMsgItem.find('tr').length;
                         if(trLen < (pageSize+1)){
                             UI.$nextPage.hide();
                         }
                         if(trLen == 0){
                             UI.$listMsgItem.append('<tr class="noData"><td colspan="6"><p class="noContent-tips">没有收藏数据了</p></td></tr>')
                         }
                     }
                 }
             })
         })
         */


    })

    var Collect = {
        getCollect : function(pageNo,pageSize){
            $.get('/userCollection/getUserCollectPojoList.do?offset='+pageNo + "&rows="+pageSize, function (data) {
                var dataJson = data.bizData;

                if (data.rtnCode == "0000000") {
                    var sum = dataJson.sum;
                    console.log(sum)
                    var  userCollectPojoList = dataJson.userCollectPojoList;
                    console.log(userCollectPojoList.length)
                    if(userCollectPojoList.length > 0){
                        UI.$nextPage.show();
                        //if(sum > pageSize){
                        //    UI.$nextPage.show();
                        //}else{
                        //    UI.$nextPage.hide();
                        //}
                        for (var i = 0; i < userCollectPojoList.length; i++) {
                            var universityName = userCollectPojoList[i].universityName
                                ,provinceName = userCollectPojoList[i].provinceName
                                ,universityType = userCollectPojoList[i].universityType
                                ,subjection = userCollectPojoList[i].subjection
                                ,universityId = userCollectPojoList[i].universityId
                                ,propertyName = userCollectPojoList[i].propertyName;
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
                            UI.$listMsgItem.append(listMsgHtml);
                        }
                        pageNo = parseInt(pageNo) + pageSize ;
                        UI.$listMsgItem.attr('pageNo', pageNo);
                        if (userCollectPojoList.length < pageSize) {
                            UI.$nextPage.hide();
                        }
                    }else{
                        UI.$nextPage.hide();
                        UI.$listMsgItem.append('<tr class="noData"><td colspan="6"><br/><br/>'+ pageErrorTip('暂无相关数据') +'<br/><br/></td></tr>')
                    }
                }

            });
        }
    };

});
