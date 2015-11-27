define(function(require, exports, module) {
    var $ = require('$');
    var Tab = {
        data:[],
        contentId:null,
        childrenTabId:null,
        getArticleHandle:null,
        parentHandle:null,
        init: function(options) {
            this.data = options.data;
            this.contentId = options.contentId;
            this.parentHandle = options.parentHandle || null;
            this.getArticleHandle = options.getArticleHandle;
            this.renderParent();
        },
        renderParent: function() {
            var parentHtml = [];
            parentHtml.push('<ul class="tabs-list mt60">');
            for (var i = 0, len = this.data.length; i < len; i++) {
                console.log(this.data[i].id  + "==" + this.data[i].name)
                if(this.data[i].name != "志愿讲堂"){
                    parentHtml.push('<li data-id="' + this.data[i].id + '">' + this.data[i].name + '</li>');
                }
            }
            parentHtml.push('</ul>');
            $('#' + this.contentId).html(parentHtml.join(''));
            $('.tabs-list li').first().addClass('active');
            this.parentEventHandle();
            if (this.parentHandle && typeof this.parentHandle === 'function') {
                this.parentHandle(this.data[0]);
            }
            if (this.data[0].category && this.data[0].category.length > 0) {
                this.renderChildren(this.data[0].category);
            }
        },
        parentEventHandle:function() {
            var that = this;
            $('.tabs-list li').on('mouseover', function(e) {
                var flag = $(this).hasClass('active');
                if (!flag) {
                    that.removeChildren();
                    $(this).addClass('active').siblings().removeClass('active');
                    console.log(that.parentHandle)
                    if (that.parentHandle && typeof that.parentHandle === 'function') {
                        that.parentHandle(that.data[$(this).index()]);
                    }
                    if (that.data[$(this).index()].category && that.data[$(this).index()].category.length > 0) {
                        that.renderChildren(that.data[$(this).index()].category);
                    }
                }
            });
        },
        removeChildren: function() {
            var children = $('.tabs-sub-list')[0];
            if (children) {
                $('.tabs-sub-list').remove();
            }
        },
        renderChildren: function(data) {
            console.log(data)
            if (data.length <= 0) {
                return;
            }
            var childrenHtml = [];
            childrenHtml.push('<ul class="tabs-sub-list mt20">');
            for (var i = 0, len = data.length; i < len; i++) {
                childrenHtml.push('<li data-id="' + data[i].id + '">' + data[i].categoryName + '</li>');
            }
            childrenHtml.push('</ul>');
            $('#' + this.contentId).append(childrenHtml.join(''));
            this.childrenEventHandle();
            $('.tabs-sub-list li').first().addClass('active');
            this.getArticleHandle(data[0].id);
        },
        childrenEventHandle: function() {
            var that = this;
            $('.tabs-sub-list li').off('mouseover');
            $('.tabs-sub-list li').on('mouseover', function(e) {
                var flag = $(this).hasClass('active');
                if (!flag) {
                    $(this).addClass('active').siblings().removeClass('active');
                    that.getArticleHandle($(this).attr('data-id'));
                }
            })
        }
    }

    module.exports = Tab;
});
