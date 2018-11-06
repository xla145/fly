layui.define(['laytpl', 'laypage', 'layer', 'form'], function (exports) {
    var $ = layui.$
        , laytpl = layui.laytpl
        , laypage = layui.laypage
        , layer = layui.layer
        , form = layui.form
        , treetable = {
            config: {} //全局配置项
        }
        , thisTable = function () {
            var that = this
                , options = that.config
                , id = options.id;

            id && (thisTable.config[id] = options);
            return {
                reload: function (options) {
                    that.reload.call(that, options);
                }
                , config: options
                , getItem: function (that) {
                    var json = JSON.parse(that.parents('.treetable-node-content').attr('data').replace(/'/g, '"'));
                    return json
                }
            }
        }
        //构造器
        , Class = function (options) {
            var that = this;
            that.config = $.extend({}, that.config, treetable.config, options);
            that.render();
        };

    Class.prototype.render = function () {
        var that = this
            , options = that.config;

        var elem = $(options.elem);
        var cols = options.cols; //json
        var res = options.data

        // header 区域模板
        var tpl_header = function (options) {
            options = options || {};
            return [
                '	<ul>',
                function () {
                    th = '';
                    $.each(cols, function (i, item) {
                        th += ['<li class="node-treetable-label" data-field="' + (item.field ? item.field : i) + '">',
                            '<div class="tree-table-cell" style="width:' + (item.width || '') + 'px" align="' + (item.align || '') + '">' + (item.title || '') + '</div>',
                            '</li>'].join("");
                    })
                    return th
                }(),
                '	</ul>',].join("");
        }

        // boady 区域模板\
        var li = '';
        var level = 1;
        var tbody = $('<div class="tree-table-tbody"></div>')

        function tpl_body(ele, res, cols, data, level) {
            $.each(res, function (i, item) {
                var hasChild = item.children && item.children.length > 0;
                var ul = $(['<ul class="treetable-node-chilrden ' + (item.spread ? "tree-table-show" : "") + '"></ul>'].join(""));
                var li = $(['<li class="treetable-node"><div class="treetable-node-content" data="' + JSON.stringify(item).replace(/\"/g, "'") + '">',
                    function () {
                        th = '';
                        $.each(cols, function (i2, item2) {
                            th += ['<div class="node-treetable-label" data-field="' + (item2.field || i2) + '">',
                                '<div class="tree-table-cell ' + (i2 == 0 ? 'title' : '') + '" align="' + (item2.align || '') + '" style="width:' + (item2.width || '') + 'px;">',
                                (i2 == 0 && data.check ? '<div class="treetableTableCheck"><input type="' + data.check + '" /><i></i></div>' : ''),
                                (i2 == 0 ? '<i class="' + (item.spread ? 'active' : '') + ' tree-table-spread layui-icon ' + (i2 == 0 && hasChild ? 'layui-icon-right' : '') + '"></i>' : ''),
                                function () {
                                    if (item2.templet) {
                                        var html = $(item2.templet).html()
                                        return laytpl(html).render(item);
                                    } else {
                                        return '<span title="' + item[item2.field] + '">' + (item[item2.field]) + '</span>'
                                    }
                                }(),
                                '</div>',
                                '</div>'].join("");
                        })
                        return th
                    }(),
                    '	</div></li>'].join(""));
                if (hasChild) {
                    li.append(ul)
                    tpl_body(ul, item.children, cols, data, level)
                }
                ele.append(li)
                typeof data.click === 'function' && that.click(li, item);
            })
        }

        tpl_body(tbody, res, cols, options, level)

        //主模板
        TPL_MAIN = ['<div class="tree-table-content"  style="width:' + (options.width || '') + 'px;height:' + (options.height || '') + 'px">'
            , '<div class="tree-table-header">'
            , tpl_header()
            , '</div>'
            , (tbody.prop("outerHTML"))
            , '</div>'].join("")
        elem.empty();
        elem.append(TPL_MAIN)
    }

    //核心入口
    treetable.render = function (options) {
        var inst = new Class(options);
        return thisTable.call(inst);
    };
    exports('treetable', treetable);
});
