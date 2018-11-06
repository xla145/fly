/**
 * @name 主页面加载
 * @author xula
 * @date 20170601
 */
layui.extend({
    setter: 'config' //配置模块
    ,navbar: 'lib/navbar'
    ,tab: 'lib/tab'
}).define(['layer','navbar','tab','setter'], function (exports) {
    var $ = layui.jquery,
        navbar = layui.navbar,
        tab = layui.tab;
    //iframe自适应
    $(window).on('resize', function () {
        var $content = $('.admin-nav-card .layui-tab-content');
        $content.height($(this).height() - 147);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();

    //设置选项卡
    tab.set({
        elem:'.admin-nav-card' //设置选项卡容器
        ,contextMenu: true
    });
    //设置navbar
    navbar.set({
        spreadOne: true,
        elem: '#admin-navbar-side',
        cached: true,
        url: '/sysNavbar/getNavbar'
    });
    //渲染navbar
    navbar.render();
    //监听点击事件
    navbar.on('click(side)', function (data) {
        tab.tabAdd(data.field);
    });

    var index = {
        cleanCached: function () { // 清除导航栏缓存
            layui.data(layui.setter.NAVBAR,null)
        },
        tabAdd: function (data) {
            tab.tabAdd(data.field);
        }
    }

    exports("index",index)
});
