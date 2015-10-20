define(function (require, exports, module) {
    var $ = require('$');
    require('bsModalManager');
    require('bsModal');
    var modalHtml = ''
        + '<div class="modal hide fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoModalLabel" aria-hidden="true">'
        + '<div class="modal-dialog" role="document">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<button type="button" class="close" id="close-btn" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
        + '<h4 class="modal-title" id="infoModalLabel" style="text-align: center;font-size: 14px;"></h4>'
        + '</div>'
        + '<div class="modal-body">'
        + '<div id="modal-content" style="font-size: 14px;"></div>'
        + '</div>'
        + '<div class="modal-footer" id="modal-footer">'
        + '</div>'
        + '</div>'
        + '</div>'
        + '</div>';

    exports.modalTips =function(title, content,footer) {
        $('body').append(modalHtml);
        $('#infoModal').modal({
            "backdrop" : "static"
        });
        $('#infoModalLabel').html(title);
        $('#modal-content').html(content);
        if(footer){
            $('#modal-footer').html(footer);
        }else{
            $('#modal-footer').remove();
        }
        $('body').on('click','#close-btn',function(){
            $('#infoModal').remove();
        })
    };






    exports.showTips = function(content){
        $('.Huialert-error').html("").slideDown('4000',function(){
            var _this = $(this);
            _this.html(content);
            setTimeout(function(){
                _this.slideUp();
            },1500)
        });
    };



    exports.Huimodal_alert = function(info,speed){
        $(document.body).append(
            '<div id="modal-alert" class="modal hide modal-alert">'+
            '<div class="modal-alert-info">'+info+'</div>'+
            '</div>'
        );
        $("#modal-alert").fadeIn();
        setTimeout(Huimodal_alert_hide(),speed);
    };
    function Huimodal_alert_hide() {
        $("#modal-alert").fadeOut("normal",function(){
            $("#modal-alert").remove();
        });
    }







});
