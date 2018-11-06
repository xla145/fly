/**
 * 读取文件 excel
 * **/

layui.define(['layer'], function (exports) {
    var Excel = function () {
        this.rABS = false;
        this.wb = null;
    };
    Excel.prototype.readExcel = function (file) {
        let that = this;
        return new Promise(function (resolve, reject) {
            let reader = new FileReader();
            reader.onload = function (e) {
                let data = e.target.result;
                if (that.rABS) {
                    that.wb = XLSX.read(btoa(this.fixdata(data)), {//手动转化
                        type: 'base64'
                    });
                } else {
                    that.wb = XLSX.read(data, {
                        type: 'binary'
                    });
                }
            };
            reader.onloadend = function () {
                resolve(XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]));
            }
            if (that.rABS) {
                reader.readAsArrayBuffer(file);
            } else {
                reader.readAsBinaryString(file);
            }
        })
    },
    Excel.prototype.fixdata = function (data) {
        //文件流转BinaryString
        let o = "", l = 0, w = 10240;
        for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
        return o;
    }
    var excel = new Excel();
    exports('excel', excel);
});