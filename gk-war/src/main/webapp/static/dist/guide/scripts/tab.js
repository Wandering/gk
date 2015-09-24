/**
 * Created by kepeng on 15/9/24.
 */

define(function(require, exports, module) {

    var $ = require('$');

    var Tab = {
        data:[],
        contentId:null,
        childrenTabId:null,
        getArticleHandle:null,
        init: function(options) {
            this.data = options.data;
            this.contentId = options.contentId;
            this.getArticleHandle = options.getArticleHandle;
            this.renderParent();
        },
        renderParent: function() {
            var parentHtml = [];
            parentHtml.push('<ul class="tabs-list mt60">');
            for (var i = 0, len = this.data.length; i < len; i++) {
                parentHtml.push('<li>' + this.data[i].name + '</li>');
            }
            parentHtml.push('</ul>');
            $('#' + this.contentId).html(parentHtml.join(''));
            $('.tabs-list li').first().addClass('active');
            this.renderChildren(this.data[0].category);
            this.parentEventHandle();
        },
        parentEventHandle:function() {
            var that = this;
            $('.tabs-list li').on('mouseover', function(e) {
                var flag = $(this).hasClass('active');
                if (!flag) {
                    that.removeChildren();
                    $(this).addClass('active').siblings().removeClass('active');
                    that.renderChildren(that.data[$(this).index()].category);
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
