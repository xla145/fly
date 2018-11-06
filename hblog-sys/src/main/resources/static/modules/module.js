// 定义接口
layui.define([], function (exports) {
    //do something
    exports('getModules', function (url, doneCallback, failCallback) {
        $.ajax({
            url: url,
            method: 'GET',
            data: {}
        }).done((data, textStatus, jqXHR) => {
            doneCallback(data, textStatus)
        }).fail((jqXHR, textStatus, errorThrown) => {
            failCallback(textStatus)
        });
    });

    exports('getSpecifies', function (moduleId, url, doneCallback, failCallback) {
        $.ajax({
            url: url,
            method: 'GET',
            data: {tempId: moduleId}
        }).done((data, textStatus, jqXHR) => {
            doneCallback(data.data, textStatus)
        }).fail((jqXHR, textStatus, errorThrown) => {
            failCallback(textStatus)
        });
    })
});