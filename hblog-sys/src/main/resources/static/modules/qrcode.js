layui.define(["form", "jquery"], function (exports) {
     var  $ = layui.jquery,
     config = {
         width: 128,
         height: 128,
         colorDark : "#000000",
         colorLight : "#ffffff",
         correctLevel : QRCode.CorrectLevel.H
     },
    //核心入口
    qrcode = function (id) {
        return new Class(id);
    };


    Class = function (id) {
       return new QRCode(id,config);
    }

    Class.prototype.makeCode = function () {
        this.makeCode();
    }

    exports("qrcode", qrcode);
})
