layui.define(["form", "jquery"], function (exports) {
    var form = layui.form,
        $ = layui.jquery,
        category = {},
        Category = function (options) {
            var that = this;
            that.config = $.extend({}, Category.config, options);
            that.ONE();
        };
    Category.config = {
        url:'/product/category/list?topId=1',
        hiddenArr:[], // 隐藏的数据，数组形式
        where: {},// 搜索条件
        parentIdPath: ''
    }
    Category.prototype.ONE = function () {
        var that = this;
        //加载一级数据
        var proHtml = '', that = this;
        var parenIds = new Array();
        if (that.config.parentIdPath !== "") {
            parenIds = that.config.parentIdPath.split("-");
        }
        $.get(that.config.url, that.config.where, function (result) {
            // proHtml += "<option value=\"\">请选择分类</option>"
            let data = result.data;
            for (let i = 0; i < data.length; i++) {
                if (that.config.hiddenArr.indexOf((data[i].catId || data[i].id)) != -1) {
                    continue;
                }
                if (Number.parseInt(parenIds[0]) === (data[i].catId || data[i].id)) {
                    proHtml += '<option selected value="' + (data[i].catId || data[i].id)+ '">' + data[i].name + '</option>';
                    that.TWO(data[i].children, parenIds[1], parenIds[2]);
                } else {
                    proHtml += '<option value="' + (data[i].catId || data[i].id) + '">' + data[i].name + '</option>';
                }
            }
            //初始化一级数据
            $(".one").append(proHtml);
            form.render();
            form.on('select(one)', function (proData) {
                // $("select[name=three]").html('<option value="">请选择分类</option>');
                var value = proData.value;
                if (value !== "" && data[$(this).index() - 1] != undefined) {
                    that.TWO(data[$(this).index() - 1].children);
                } else {
                    $(".two").attr("disabled", "disabled").find("option:selected").val("");
                }
                form.render();
            });
        })
    }
    //加载二级数据
    Category.prototype.TWO = function (twos, parentId, childId) {
        var twoHtml = '<option value="">请选择分类</option>', that = this;
        for (var i = 0; i < twos.length; i++) {
            if (Number.parseInt(parentId) === (twos[i].catId || twos[i].id)) {
                twoHtml += '<option selected value="' + (twos[i].catId || twos[i].id) + '">' + twos[i].name + '</option>';
                that.three(twos[i].children, childId);
            } else {
                twoHtml += '<option value="' + (twos[i].catId || twos[i].id) + '">' + twos[i].name + '</option>';
            }
        }
        $(".two").html(twoHtml).removeAttr("disabled");
        form.render();
        form.on('select(two)', function (cityData) {
            var value = cityData.value;
            if (value !== "" && twos[$(this).index() - 1] != undefined) {
                that.three(twos[$(this).index() - 1].children, childId);
            } else {
                $("select[name=three]").attr("disabled", "disabled").find("option:selected").val("");
                ;
            }
            form.render();
        });
    }

    //加载三级数据
    Category.prototype.three = function (threes, parentId) {
        var threeHtml = '<option value="">请选择分类</option>';
        for (var i = 0; i < threes.length; i++) {
            if (Number.parseInt(parentId) === (threes[i].catId || threes[i].id)) {
                threeHtml += '<option selected value="' + (threes[i].catId || threes[i].id) + '">' + threes[i].name + '</option>';
            } else {
                threeHtml += '<option value="' + (threes[i].catId || threes[i].id) + '">' + threes[i].name + '</option>';
            }
        }
        $(".three").html(threeHtml).removeAttr("disabled");
        form.render();
    }

    // var category = new Category();

    //核心入口
    category.render = function (options) {
        new Category(options);
    };
    exports("category", category);
})
