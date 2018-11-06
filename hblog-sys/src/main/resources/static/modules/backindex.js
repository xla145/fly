layui.use(['form', 'table', 'upload', 'common'], function () {
    var getModules = layui.getModules
    formModule();
    initGoodsSpec();
    let form = layui.form
    form.verify({
        stock: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value) {
                if (!(/^\d+$/.test(value))) {
                    return '库存必须为正整数';
                }
            }
        },
        price: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value) {
                if (!(/^[0-9]+(.[0-9]{2})?$/.test(value))) {
                    return '价格最多两位小数';
                }
            }
        },
        buyPrice: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value) {
                if (!(/^[0-9]+(.[0-9]{2})?$/.test(value))) {
                    return '进货价最多两位小数';
                }
            }
        }
    });
    form.on('submit(submitForm)', function (data) {
        submitFun()
        return false;
    });

})

function submitFun() {
    let res = formTableData()
    AjaxSubmit(res.res)
    return false;
}

function formTableData() {
    let tbodyObj = $('#table #tbody')
    let res = []
    let res2 = {}
    if (tbodyObj.length > 0) {
        let rows = tbodyObj[0].rows
        let rowLen = rows.length
        let cellLen = rows[0].cells.length
        for (var i = 0; i < rowLen; i++) {
            let row = rows[i]
            let str = row.getAttribute('data-str')
            let obj2 = {id: str}
            res2[str] = {}
            for (var j = cellLen - 1; j > cellLen - 9; j--) {
                let cell = row.cells[j]
                let cellInput = $(cell).find('input.layui-input')[0]
                if (cellInput) {
                    let keyname = cellInput.getAttribute('data-key')
                    let keyvalue = ''
                    if (cellInput.type === 'radio') {
                        if (cellInput.checked) {
                            keyvalue = cellInput.value ? cellInput.value : ''
                        }
                    }
                    else {
                        keyvalue = cellInput.value
                    }
                    obj2 = Object.assign(obj2, {[keyname]: keyvalue})
                    res2[str] = obj2
                }
            }
            res.push(obj2)
        }
    }
    return {
        res: res,
        res2: res2
    };
}

/*
 形成模型HTML
 */
function formModule() {
    let getSpecifies = layui.getSpecifies, form = layui.form
    // 获取选中的模型名称
    form.on('select(moduleSelect)', function (data) {
        $('#tempId').val(data.value)
        getSpecifies(data.value, '/goods/spec/getSpec', formSpecify, faillSpecify)
    })
}

function failFormModule(textStatus) {
    console.log(textStatus)
}

/*
 形成规格HTML
 */
function formSpecify(data) {
    $('#table').empty();
    let table = layui.table
    table.render({
        elem: '#specifyTable',
        //支持所有基础参数
        data: data,
        cols: [[
            {field: 'name', title: '规格', width: 80} //这里的templet值是模板元素的选择器
            , {field: 'specify', title: '详情', templet: '#titleTpl', style: 'height:auto'}
        ]],
        done: function (res, curr, count) {

        }
    })
}

function faillSpecify(textStatus) {
    console.log(textStatus)
}

/*
* 获取分类列表
*/
function getCateList() {
    let res = formTableData()
    thisGoodsSpecPriceJson = res.res2
    let target = window.event.target
    if ($(target).hasClass('layui-btn-normal')) {
        $(target).removeClass('layui-btn-normal')
        $(target).addClass('layui-btn-primary')
    }
    else {
        $(target).removeClass('layui-btn-primary')
        $(target).addClass('layui-btn-normal')
    }
    ajaxGetSpecInput()
}

/**
 *  点击商品规格触发 下面输入框显示
 */
function ajaxGetSpecInput() {
    var spec_arr = {}// 用户选择的规格数组
    // 选中了哪些属性
    $('button.specifyButton').each(function () {
        if ($(this).hasClass('layui-btn-normal')) {
            var spec_id = $(this).attr('spec_id')
            var item_id = $(this).attr('item_id')
            var spec_name = $(this).attr('spec_name')
            if (!spec_arr.hasOwnProperty(spec_id))
                spec_arr[spec_id] = {
                    spec_id: spec_id,
                    spec_name: spec_name,
                    count: 1,
                    specs: []
                }
            spec_arr[spec_id].specs.push({
                id: item_id,
                name: $(this)[0].innerHTML,
                spec_name: spec_name
            })
            spec_arr[spec_id].count = spec_arr[spec_id].specs.length
        }
    })
// 将对象转化为数组，然后按照属性排序再转化为对象
    let max = Object.values(spec_arr)[0]
    let ab = Object.values(spec_arr)
    ab.sort((a, b) => {
        if (a.count < b.count) {
            return -1
        }
        if (a.count > b.count) {
            return 1
        }
        // a must be equal to b
        return 0
    })
    let newSpec = {}
    ab.map((v, k) => {
        newSpec[v.spec_id] = v
    })
    ajaxGetSpecInput2(newSpec) // 显示下面的输入框
}

function compare(a, b) {
    if (a.count < b.count) {
        return -1
    }
    if (a.count > b.count) {
        return 1
    }
    // a must be equal to b
    return 0
}

/**
 * 根据用户选择的不同规格选项
 * 返回 不同的输入框选项
 */
function ajaxGetSpecInput2(spec_arr) {
    let trHtml = '<tr>'
    for (let skey in spec_arr) {
        let item = spec_arr[skey]
        trHtml += '<td data-id="' + item.spec_id + '"><b>' + item.spec_name + '</b></td>'
        let specLen = item.specs.length
    }
    trHtml += '<td>库存<\/td><td>销量<\/td><td>进货价<\/td><td>售价<\/td><td>商品封面<\/td><td>规格图标<span class="marked">【比例1:1】</span><\/td><td>是否默认显示<\/td>'
    trHtml += '<\/tr>'
    $('#table').html(trHtml)
    ajaxGetSpecInput3(spec_arr)
}

function ajaxGetSpecInput3(spec_arr) {
    let finalArr = []
    var result = []
    for (let skey in spec_arr) {
        let item = spec_arr[skey]
        finalArr.push(item.specs)
    }

    function descartes(arrIndex, aresult) {
        if (arrIndex >= finalArr.length) {
            result.push(aresult)
            return
        }
        var aArr = finalArr[arrIndex]
        for (var i = 0; i < aArr.length; ++i) {
            var theResult = aresult.slice(0, aresult.length)
            theResult.push(aArr[i])
            descartes(arrIndex + 1, theResult)
        }
    }

    descartes(0, [])
    fromTable(result)
}

function fromTable(result) {
    let form = layui.form, common = layui.common
    let upload = layui.upload; //得到 upload 对象
    let trhtml = document.createElement('tbody')
    trhtml.setAttribute('id', 'tbody')
    result.map((v, k) => {
        let trobj = document.createElement('tr')
        let html = ''
        let str = ''
        let strArr = []
        let desc = ''
        v.map((a, b) => {
            html += '<td data-id="' + a.id + '">' + a.name + '<\/td>'
            strArr.push(a.id)
            desc += a.spec_name + ':' + a.name + ','
        })
        if (desc.length > 0) {
            desc = desc.substring(0, desc.length - 1)
        }
        str = strArr.join('_')
        let item = {
            stock: '',
            sales: '',
            price: '',
            buyprice: '',
            specImg: '',
            coverImg: '',
            isDefault: ''
        }
        let radioChecked = ''
        strArr.sort()
        for (let key in thisGoodsSpecPriceJson) {
            let keyArr = key.split('_')
            keyArr.sort()
            let a = keyArr.join(',')
            let b = strArr.join(',')
            if (a === b) {
                item = thisGoodsSpecPriceJson[key]
                if (item.isDefault != "") {
                    radioChecked = 'checked'
                }
            }
        }
        //<a href="javasctipt:void(0)" class="img-btn img-pvw">预览</a>
        html += '<td><input class="stock layui-input" lay-verify="stock" data-key="stock"  type="text" value="' + item.stock + '"><\/td>'
        html += '<td><input class="sales layui-input" lay-verify="sales" data-key="sales"  type="text" value="' + item.sales + '"><\/td>'
        html += '<td><input class="buyprice layui-input" lay-verify="buyPrice" data-key="buyprice" type="text" value="' + item.buyprice + '"><\/td>'
        html += '<td><input class="price layui-input" lay-verify="price" data-key="price"  type="text" value="' + item.price + '"><\/td>'
        html += '<td style="text-align: left;">'
        if (item.coverImg) {
            html += '<span id="specImgPvw"><button type="button" class="layui-btn layui-btn-xs layui-btn-normal img-pvw"><i class="layui-icon" style="color: #FFF;">&#x1005;</i> 预留图片</button>'
            html += '<a href="javasctipt:void(0)" class="img-btn img-del">删除</a></span>'
            html += '<span id="specImgUpload" style="display:none"><button type="button" class="layui-btn layui-btn-xs layui-btn-primary upload-spec-img"><i class="layui-icon">&#xe61f;</i> 选择图片</button></span>'
        } else {
            html += '<span id="specImgPvw" style="display:none"><button type="button" class="layui-btn layui-btn-xs layui-btn-normal img-pvw"><i class="layui-icon" style="color: #FFF;">&#x1005;</i> 预留图片</button>'
            html += '<a href="javasctipt:void(0)" class="img-btn img-del">删除</a></span>'
            html += '<span id="specImgUpload"><button type="button" class="layui-btn layui-btn-xs layui-btn-primary upload-spec-img"><i class="layui-icon">&#xe61f;</i> 选择图片</button></span>'
        }
        html += '<input type="hidden" value="' + item.coverImg + '" data-key="coverImg" class="spec-img layui-input"><\/td>'

        html += '<td style="text-align: left;">'
        if (item.specImg) {
            html += '<span id="specImgPvw"><button type="button" class="layui-btn layui-btn-xs layui-btn-normal img-pvw"><i class="layui-icon" style="color: #FFF;">&#x1005;</i> 预留图片</button>'
            html += '<a href="javasctipt:void(0)" class="img-btn img-del">删除</a></span>'
            html += '<span id="specImgUpload" style="display:none"><button type="button" class="layui-btn layui-btn-xs layui-btn-primary upload-spec-img"><i class="layui-icon">&#xe61f;</i> 选择图片</button></span>'
        } else {
            html += '<span id="specImgPvw" style="display:none"><button type="button" class="layui-btn layui-btn-xs layui-btn-normal img-pvw"><i class="layui-icon" style="color: #FFF;">&#x1005;</i> 预留图片</button>'
            html += '<a href="javasctipt:void(0)" class="img-btn img-del">删除</a></span>'
            html += '<span id="specImgUpload"><button type="button" class="layui-btn layui-btn-xs layui-btn-primary upload-spec-img"><i class="layui-icon">&#xe61f;</i> 选择图片</button></span>'
        }
        html += '<input type="hidden" value="' + item.specImg + '" data-key="specImg" class="spec-img layui-input"><\/td>'
        html += '<td><div>' + '<input type="radio" class="isDefault layui-input" data-key="isDefault"  name="isDefault" value="1" title="是" ' + radioChecked + '> </div><\/td>'
        html += '<td style="display:none"><input class="desc layui-input"  data-key="desc"  type="hidden" value="' + desc + '"><\/td>'
        trobj.innerHTML = html
        trobj.setAttribute('data-str', str)
        trhtml.append(trobj)
    });
    $('#table').append(trhtml);
    form.render('radio', 'tableForm')

    var bindImgOper = function () {
        $(".img-pvw").on("click", function () {
            common.showImage($(this).parent('#specImgPvw').siblings('.spec-img').val(), 300);
        });
        $(".img-del").on("click", function () {
            $(this).parent('#specImgPvw').siblings('.spec-img').val("");
            $(this).parent('#specImgPvw').hide().siblings('#specImgUpload').show();
        });
    }

    //图片上传
    var uploadInst = upload.render({
        elem: '.upload-spec-img' //绑定元素
        , url: '/oss/product/upload' //上传接口
        , done: function (res) {
            $(this.item).parent('#specImgUpload').siblings('.spec-img').val(res.data[0]);
            $(this.item).parent('#specImgUpload').hide().siblings('#specImgPvw').show();
            layer.msg("上传成功!");
        }
        , error: function () {
            //请求异常回调 TODO 干个弹框出去！！
        }
    });

    bindImgOper()
    hedyg()
}

function hedyg() {
    var tab = $('#table #tbody')[0] //要合并的tableID
    let celllen = tab.rows[0].cells.length
    let rowlen = tab.rows.length
    if (celllen > 1 && rowlen > 1) {
        for (var i = 0; i < celllen; i++) {
            let count = 1, startCellIndex = i, startIndex = 0
            let firObj = $(tab.rows[0].cells[i])
            let firId = firObj.attr('data-id')
            for (var j = 1; j < rowlen; j++) {
                let attrobj = $(tab.rows[j].cells[i])
                let attrid = attrobj.attr('data-id')
                if (attrid) {
                    if (attrid == firId) {
                        attrobj.css({display: 'none'})
                        count++
                        if (j == rowlen - 1) {
                            $(tab.rows[startIndex].cells[startCellIndex]).attr('rowspan', count)
                            count = 1
                        }
                    }
                    if (firId != attrid) {
                        $(tab.rows[startIndex].cells[startCellIndex]).attr('rowspan', count)
                        startIndex = j
                        startCellIndex = i
                        count = 1
                        firId = attrid
                    }
                }
            }
        }
    }
}

//初始化商品规格&规格价格数据
function initGoodsSpec() {
    //初始化规格数据
    if (thisGoodsSpecJson) {
        formSpecify(thisGoodsSpecJson);
        ajaxGetSpecInput();
    }

}
