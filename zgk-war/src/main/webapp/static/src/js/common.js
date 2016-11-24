var Common = {
    init: function () {
        this.renderMenu();
    },
    url: {},
    cookie: {
        /*
         *功能：设置Cookie
         *cookieName 必选项，cookie名称
         *cookieValue 必选项，cookie值
         *seconds 生存时间，可选项，单位：秒；默认时间是3600秒
         *path cookie存放路径，可选项
         *domain cookie域，可选项
         *secure 安全性，指定Cookie是否只能通过https协议访问，一般的Cookie使用HTTP协议既可访问，如果设置了Secure（没有值），则只有当使用https协议连接时cookie才可以被页面访问
         */
        setCookie: function (cookieName, cookieValue, seconds, path, domain, secure) {
            var expires = new Date();
            var seconds = arguments[2] ? arguments[2] : (3600 * 5);
            expires.setTime(expires.getTime() + seconds * 1000);
            document.cookie = escape(cookieName) + '=' + escape(cookieValue) + (expires ? ';expires=' + expires.toGMTString() : '') + (path ? ';path=' + path : '/') + (domain ? ';domain=' + domain : '') + (secure ? ';secure' : '');
        },
        /*
         *功能：获取Cookie
         *name 必选项，cookie名称
         */
        getCookie: function (name) {
            var cookie_start = document.cookie.indexOf(name);
            var cookie_end = document.cookie.indexOf(";", cookie_start);
            return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
        },
        /*
         *功能：删除或清空Cookie
         *name 必选项，cookie名称
         */
        delCookie: function (name, value) {
            var value = arguments[1] ? arguments[1] : null;
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var val = Common.cookie.getCookie(name);
            if (val != null) {
                document.cookie = name + '=' + value + ';expires=' + exp.toGMTString();
            }
        }
    },
    ajaxFun: function (url, method, reqData, callback, callbackError, isasync,acceptType) {
        var isasyncB = null;
        if (isasync) {
            isasyncB = false;
        } else {
            isasyncB = true;
        }
        var headers=null;
        if(acceptType){
            headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }else{
            headers='';
        }

        $.ajax({
            url: url,
            type: method,
            data: reqData || {},
            async: isasyncB,
            success: callback,
            error: callbackError,
            headers:headers
        });
    },
    getLinkey: function getLinkey(name) {
        var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
        if (reg.test(window.location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
        return "";
    },
    renderMenu:function(){
        var pathName = window.location.pathname;
        if(Common.cookie.getCookie('siderMenu')){
            var siderMenu = $.parseJSON(Common.cookie.getCookie('siderMenu'));
            var menus = [];
            $.each(siderMenu,function(i,v){

                if(pathName==v.meunUrl){
                    menus.push('<li class="nav-li active">');
                }else{
                    menus.push('<li class="nav-li">');
                }
                if(v.sonMeuns.length==0){
                    menus.push('<a href="'+ v.meunUrl +'" class="dropdown-toggle" id="'+ v.meunId +'">');
                }else{
                    menus.push('<a href="javascript:;" class="dropdown-toggle" id="'+ v.meunId +'">');
                }
                menus.push('<i class="icon-desktop"></i>');
                menus.push('<span class="menu-text">'+ v.meunName +'</span>');
                menus.push('</a>');
                //console.log("子菜单:"+v.sonMeuns.length)
                if(v.sonMeuns.length>0){
                    //console.log(v.sonMeuns)
                    menus.push('<ul class="submenu">');
                    $.each(v.sonMeuns,function(k,m){
                        if(pathName == m.meunUrl){
                            menus.push('<li class="active">');
                        }else{
                            menus.push('<li>');
                        }
                        menus.push('<a href="'+ m.meunUrl +'" id="'+ m.meunId +'"><i class="icon-double-angle-right"></i>'+ m.meunName +'</a>');
                        menus.push('</li>');
                    });
                    menus.push('</ul>');
                }
                menus.push('</li>');
            });
            $('#nav-list').append(menus.join(''));
            $('body').find('.submenu li.active').parents('li.nav-li').addClass('open active');
        }
    },
};
Common.init();