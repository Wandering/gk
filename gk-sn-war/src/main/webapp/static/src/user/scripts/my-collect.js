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
            Collect.getCollect(pageNo, pageSize);
        }).click();

        $('#collect-table').on('click','.cancel-collect',function(){
            var $this = $(this);
            var universityId = $this.attr('universityId');
            $.get('/userCollection/deleteUserCollect.do?universityId='+universityId,function(data){
                console.log(data);
                if(data.rtnCode == '0000000'){
                    UI.$listMsgItem.html('');
                    if(data.bizData){
                        $this.parents('tr').remove();
                        $('tr.noData').hide();
                        UI.$listMsgItem.attr('pageNo','0');
                        var pageNo = UI.$listMsgItem.attr('pageNo');
                        Collect.getCollect(pageNo, pageSize);
                        var trLen = UI.$listMsgItem.find('tr').length;
                        if(trLen < (pageSize+1)){
                            UI.$nextPage.hide();
                        }
                    }
                }
            })
        })
    });

    var Collect = {
        getCollect : function(pageNo,pageSize){
            $.get('/userCollection/getUserCollectPojoList.do?offset='+pageNo + "&rows="+pageSize, function (data) {
                var dataJson = data.bizData;
                if (data.rtnCode == "0000000") {
                    var sum = dataJson.sum;
                    if(sum==0){
                        UI.$listMsgItem.append('<tr class="noData"><td colspan="6"><br/><br/>'+ pageErrorTip('暂无相关数据') +'<br/><br/></td></tr>')
                    }
                    var  userCollectPojoList = dataJson.userCollectPojoList;
                    if(userCollectPojoList.length > 0){
                        if(sum > userCollectPojoList.length ){
                            UI.$nextPage.show();
                        }
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
                    }
                }
            });
        }
    };

});
