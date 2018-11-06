layui.define(['laytpl', 'layer', 'element', 'util'], function(exports){
  exports('setter', {
    container: 'LAY_app' //容器ID
    ,base: layui.cache.base //记录静态资源所在路径
    ,views: layui.cache.base + 'tpl/' //动态模板所在目录
    ,entry: 'index' //默认视图文件名
    ,engine: '.html' //视图文件后缀名
    ,pageTabs: true //是否开启页面选项卡功能。iframe 常规版推荐开启

    ,name: 'layuiAdmin'
    ,tableName: 'layuiAdmin' //本地存储表名
    ,MOD_NAME: 'admin' //模块事件名
    ,NAVBAR: 'yuelinghui_navbar' // 菜单名

    ,debug: true //是否开启调试模式。如开启，接口异常时会抛出异常 URL 等信息
    ,ctx: '/' //请求的所在路径
    //自定义请求字段
    ,request: {
      tokenName: false //自动携带 token 的字段名（如：access_token）。可设置 false 不携带。
    }

    //自定义响应字段
    ,response: {
      statusName: 'code' //数据状态的字段名称
      ,statusCode: {
        ok: 0 //数据状态一切正常的状态码
        ,logout: 1001 //登录状态失效的状态码
      }
      ,msgName: 'msg' //状态信息的字段名称
      ,dataName: 'data' //数据详情的字段名称
    }

    //扩展的第三方模块
    ,extend: [
      'echarts', //count 核心包
      'echartsTheme', //count 主题
      'clipboard', // 粘贴板
      'qrcode.min', // 生成二维码
      'xlsx.full.min' // excel处理
    ]
  });
});
