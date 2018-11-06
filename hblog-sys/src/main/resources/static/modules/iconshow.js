/**
 * 菜单选择图标
 * **/

layui.define(function (exports) {
    var $ = layui.$, allicons = ["layui-icon-rate-half", "layui-icon-rate", "layui-icon-rate-solid", "layui-icon-cellphone",
        "layui-icon-vercode", "layui-icon-login-wechat", "layui-icon-login-qq", "layui-icon-login-weibo",
        "layui-icon-password", "layui-icon-username", "layui-icon-refresh-3", "layui-icon-auz", "layui-icon-spread-left",
        "layui-icon-shrink-right", "layui-icon-snowflake", "layui-icon-tips", "layui-icon-note", "layui-icon-home",
        "layui-icon-senior", "layui-icon-refresh", "layui-icon-refresh-1", "layui-icon-flag", "layui-icon-theme", "layui-icon-notice",
        "layui-icon-website", "layui-icon-console", "layui-icon-face-surprised", "layui-icon-set", "layui-icon-template-1", "layui-icon-app",
        "layui-icon-template", "layui-icon-praise", "layui-icon-tread", "layui-icon-male", "layui-icon-female", "layui-icon-camera",
        "layui-icon-camera-fill", "layui-icon-more", "layui-icon-more-vertical", "layui-icon-rmb", "layui-icon-dollar",
        "layui-icon-diamond", "layui-icon-fire", "layui-icon-return", "layui-icon-location", "layui-icon-read", "layui-icon-survey",
        "layui-icon-face-smile", "layui-icon-face-cry", "layui-icon-cart-simple", "layui-icon-cart", "layui-icon-next", "layui-icon-prev",
        "layui-icon-upload-drag", "layui-icon-upload", "layui-icon-download-circle", "layui-icon-component", "layui-icon-file-b", "layui-icon-user",
        "layui-icon-find-fill", "layui-icon-loading", "layui-icon-loading-1", "layui-icon-add-1", "layui-icon-play", "layui-icon-pause", "layui-icon-headset",
        "layui-icon-video", "layui-icon-voice", "layui-icon-speaker", "layui-icon-fonts-del", "layui-icon-fonts-code", "layui-icon-fonts-html", "layui-icon-fonts-strong",
        "layui-icon-unlink", "layui-icon-picture", "layui-icon-link", "layui-icon-face-smile-b", "layui-icon-align-left", "layui-icon-align-right", "layui-icon-align-center",
        "layui-icon-fonts-u", "layui-icon-fonts-i", "layui-icon-tabs", "layui-icon-radio", "layui-icon-circle", "layui-icon-edit", "layui-icon-share", "layui-icon-delete",
        "layui-icon-form", "layui-icon-cellphone-fine", "layui-icon-dialogue", "layui-icon-fonts-clear", "layui-icon-layer", "layui-icon-date", "layui-icon-water",
        "layui-icon-code-circle", "layui-icon-carousel", "layui-icon-prev-circle", "layui-icon-layouts", "layui-icon-util", "layui-icon-templeate-1",
        "layui-icon-upload-circle", "layui-icon-tree", "layui-icon-table", "layui-icon-chart", "layui-icon-chart-screen", "layui-icon-engine", "layui-icon-triangle-d",
        "layui-icon-triangle-r", "layui-icon-file", "layui-icon-set-sm", "layui-icon-add-circle", "layui-icon-404", "layui-icon-about", "layui-icon-up", "layui-icon-down",
        "layui-icon-left", "layui-icon-right", "layui-icon-circle-dot", "layui-icon-search", "layui-icon-set-fill", "layui-icon-group", "layui-icon-friends", "layui-icon-reply-fill",
        "layui-icon-menu-fill", "layui-icon-log", "layui-icon-picture-fine", "layui-icon-face-smile-fine", "layui-icon-list", "layui-icon-release", "layui-icon-ok", "layui-icon-help",
        "layui-icon-chat", "layui-icon-top", "layui-icon-star",
        "layui-icon-star-fill", "layui-icon-close-fill", "layui-icon-close", "layui-icon-ok-circle", "layui-icon-add-circle-fine"];
    var iconshow = {
        render: function (options) {
            let that = this;
            that.options = options || {};
            var html = '<div class="popover"><div class="popover-title"><input type="search" class="form-control iconpicker-search" placeholder="查找图标"></div><div class="iconpicker-items"><ul class="icon_lists"></ul></div></div>'
            if ($('.popover').length <= 0) {
                $('body').append(html)
            }
            for (let icon of allicons) {
                $('.icon_lists').append('<li class="hov" data-icon="layui-icon ' + icon + '"><i class="layui-icon ' + icon + '"></i></li>');
            }
            let btn = that.options.e;
            let icon = that.options.el;
            btn.on('click',function () {
                if ($('.popover').css('display') == "none") {
                    $('.popover').css({
                        'left': btn.offset().left,
                        'top': btn.offset().top + btn[0].clientHeight + 5
                    });
                    $('.popover').slideDown(150);
                } else {
                    $(".popover").slideUp(150);
                }

            }), $(document).click(function () {
                $(".popover").slideUp(150);
            }), $(btn).click(function (event) {
                event.stopPropagation();
            }), $('.popover').click(function (event) {
                event.stopPropagation();
            }), $('.icon_lists li').click(function () {
                $(this).addClass('selectd').removeClass('hov').siblings().removeClass('selectd');
                icon.val($(this).attr("data-icon"));
            }), $('.iconpicker-search').keyup(function () {
                var str = $(this).val();
                var icons = [];
                $('.icon_lists li').each(function () {
                    let b = $(this);
                    let e = b.attr("data-icon").toLowerCase();
                    let f = false;
                    try {
                        f = new RegExp(str, "g");
                    } catch (a) {
                        f = false;
                    }
                    if (f !== false && e.match(f)) {
                        icons.push(b);
                        b.show();
                    } else {
                        b.hide();
                    }
                })

            })
        }
    }
    exports('iconshow', iconshow);
});

