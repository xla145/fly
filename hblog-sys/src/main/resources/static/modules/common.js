
layui.extend({
    setter: '../config' //配置模块
}).define(['layer', 'form','util','setter'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer, form = layui.form;

    var common = {
        /**
         * 抛出一个异常错误信息
         * @param {String} msg
         */
        throwError: function (msg) {
            throw new Error(msg);
            return;
        },
        /**
         * 弹出一个错误提示
         * @param {String} msg
         */
        msgError: function (msg) {
            layer.msg(msg, {
                icon: 5
            });
            return;
        },
        /**
         *   将post的数据转换成key-val的格式，支持基本数据类型的数组参数转换为多个key-val，不支持对象转换需自己转换后调用请求
         * */
        arrayTokeyval: function (data) { // 将post的数据转换成key-val的格式，支持基本数据类型的数组参数转换为多个key-val，不支持对象转换需自己转换后调用请求
            let ret = ''
            for (let it in data) {
                if (data[it] instanceof Array) {
                    for (let arr of data[it]) {
                        ret += encodeURIComponent(it) + '=' + encodeURIComponent(arr) + '&'
                    }
                } else {
                    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
                }
            }
            return ret
        },
        layerShowIframe: function (url, title, isFull) {//弹出新窗口
            var index = layer.open({
                type: 2,
                title: title,
                shadeClose: true,
                shade: 0.5,
                maxmin: true, //开启最大化最小化按钮
                offset: ['0px'],
                area: ['1000px', '600px'],
                content: url
            });
            if (isFull) {
                layer.full(index)
            }
            return index;
        },
        addForm: function (options) {//弹出窗口 类型为1
            var success = options.success
            let index = layer.open($.extend({
                type: 1,
                title: "",
                shadeClose: true,
                shade: 0.5,
                maxmin: true, //开启最大化最小化按钮
                area: ['1000px', '600px'],
                content: "",
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    typeof success === 'function' && success.apply(this, arguments);
                },
                btn2: function (index, layero) {
                    var formEm = $(layero).find('form');
                    formEm[0].reset();
                },
                cancel: function (index, layero) {
                    var formEm = $(layero).find('form');
                    formEm[0].reset();
                }
            }, options));
            let isFull = options.isFull;
            if (isFull) {
                layer.full(index);
            }
        },
        editForm: function (options) {//弹出窗口 类型为1
            var success = options.success
            let isFull = options.isFull;
            let index = layer.open($.extend({
                type: 2,
                title: "",
                shadeClose: true,
                shade: 0.5,
                maxmin: true, //开启最大化最小化按钮
                area: ['1000px', '600px'],
                content: "",
                btn: ['保存', '取消'],
                success: function (layero, index) {
                    typeof success === 'function' && success.apply(this, arguments);
                },
                btn2: function (index, layero) {
                    var formEm = $(layero).find('iframe').contents().find('form');
                    formEm[0].reset();
                },
                cancel: function (index, layero) {
                    var formEm = $(layero).find('iframe').contents().find('form');
                    formEm[0].reset();
                }
            }, options));
            if (isFull) {
                layer.full(index);
            }
        },
        formatForm: function (form) {
            var a = form.serializeArray();
            var o = {};
            $.each(a, function () {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        },
        infoForm: function (options) {//弹出窗口 类型为1
            var index = layer.open($.extend({
                type: 2,
                title: "",
                shadeClose: true,
                shade: 0.5,
                maxmin: true, //开启最大化最小化按钮
                content: "",
                btn:''
            }, options));
            let isFull = options.isFull;
            if (isFull) {
                layer.full(index);
            }
        },
        post: function (url, data, callback) {
            if ($.isFunction(data)) {
                callback = data;
                data = undefined;
            }
            $.ajax({
                type: "POST",
                url: url,
                data: common.arrayTokeyval(data),
                success: callback
            });
        },
        stampToDate: function (stamp) {
            if (stamp == undefined || stamp == "") {
                return "";
            }
            var newDate = new Date();
            var stampArr = stamp.split(",");
            var dateArr = new Array();
            for (var i = 0; i < stampArr.length; i++) {
                var newDate = new Date();
                newDate.setTime(stampArr[i]);
                dateArr.push(newDate.Format("yyyy-MM-dd"));
            }
            return dateArr.join(" , ");
        },
        showImage: function (url, showMaxWidth) {
            if (url !== null) {
                layui.img(url, function (img) {
                    let width = img.naturalWidth;
                    if (width > showMaxWidth) {
                        width = showMaxWidth;
                    }
                    layer.open({
                        type: 1,
                        title: false,
                        closeBtn: 0,
                        area: width,
                        skin: 'layui-layer-nobg', //没有背景色
                        shadeClose: true,
                        content: '<img src="' + url + '" width="' + width + 'px" />'
                    });
                }, function (e) {
                    layer.msg("图片加载失败！", {icon: 5});
                })
            }
        },
        // 去除数据中的空值
        filterArray: function(array) {
            return array.filter(item=>item);
        },
        copyUrl: function (text) {
            let textArea = document.createElement("textarea")
            textArea.style.position = 'fixed'
            textArea.style.top = 0
            textArea.style.left = 0
            textArea.style.width = '2em'
            textArea.style.height = '2em'
            textArea.style.padding = 0
            textArea.style.border = 'none'
            textArea.style.outline = 'none'
            textArea.style.boxShadow = 'none'
            textArea.style.background = 'transparent'
            textArea.value = text
            document.body.appendChild(textArea)
            textArea.select()
            try {
                document.execCommand('copy') ? layer.msg("复制成功！") : layer.msg("复制失败！")
            } catch (err) {
                this.throwError('不能使用这种方法复制内容'+err.toString())
            }
            document.body.removeChild(textArea)
        },
        clearNaverCache: function () {
            layui.data(layui.setter.NAVBAR,null)
        },
        stringToIntArray: function (arrays) {
            let dataIntArr = [];//保存转换后的整型字符串
            arrays.forEach(function(data,index,arr){
                dataIntArr.push(+data);
            });
            return dataIntArr;
        }
    };
    exports('common', common);
});
