/**
 * 工作流显示
 */
layui.define(function (exports) {
    var MOD_NAME = "step";
    class Step {
        constructor() {
            this.config = {
                id:'',
                data:[],
                current:[0]
            }
        }
        render(options) {
            this.config = Object.assign({},this.config,options);
            if (this.config.id === undefined) {
                throw new Error("容器编号不能为空！");
                return;
            }
            if (this.config.data === undefined) {
                throw new Error("渲染数据不能为空！");
                return;
            }
            let html = "<div class=\"layui-steps layui-steps--horizontal\">";
            let current = this.config.current
            let percent = 100/(this.config.data.length)
            let index = 0;
            layui.each(this.config.data,function (i,j) {
                i++;
                if (i > current.length) {
                    html +="    <div class=\"layui-step is-horizontal is-center\" style=\"flex-basis: "+percent+"%; margin-right: 0px;\">\n" +
                        "        <div class=\"layui-step__head is-wait\">\n" +
                        "            <div class=\"layui-step__line\" style=\"margin-right: 0px;\"><i class=\"layui-step__line-inner\"\n" +
                        "                                                                     style=\" border-width: 1px; width: 100%;\"></i>\n" +
                        "            </div>\n" +
                        "            <div class=\"layui-step__icon is-text\"><!---->\n" +
                        "                <div class=\"layui-step__icon-inner\">"+i+"</div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"layui-step__main\">\n" +
                        "            <div class=\"layui-step__title is-wait\">"+j.name+"</div>\n";
                    html += "        </div>\n" +
                        "    </div>";
                } else {
                    html +="    <div class=\"layui-step is-horizontal is-center\" style=\"flex-basis: "+percent+"%; margin-right: 0px;\">\n" +
                        "        <div class=\"layui-step__head is-finish\">\n" +
                        "            <div class=\"layui-step__line\" style=\"margin-right: 0px;\"><i class=\"layui-step__line-inner\"\n" +
                        "                                                                     style=\" border-width: 1px; width: 100%;\"></i>\n" +
                        "            </div>\n" +
                        "            <div class=\"layui-step__icon is-text\"><!---->\n" +
                        "                <div class=\"layui-step__icon-inner\">"+i+"</div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <div class=\"layui-step__main\">\n" +
                        "            <div class=\"layui-step__title is-finish\">"+j.name+"</div>\n";
                    for (let s of j.status) {
                        if (s.id == current[index]) {
                            html += "<div class=\"layui-step__description is-finish\">"+s.name+"</div>"
                        }
                    }
                    index ++;
                    html += "        </div>\n" +
                        "    </div>";
                }
            })
            html+="</div>";
            document.getElementById(this.config.id).innerHTML = html
        }
    }
    var step = new Step();
    exports(MOD_NAME, step);
})