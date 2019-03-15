var Editor = function (options) {
};
Editor.defaults = {
    editorEm: undefined,
    lang: 'zh-CN',
    minHeight: 100,
    placeholder: '请输入内容....',
    uploadPath: '/cas/product/upload'
};
Editor.prototype = {
    init: function (options) {
        this.config = $.extend(true, {}, Editor.defaults, options);
        var that = this;
        if (that.config.editorEm === undefined) {
            layer.msg('Error:请配置编译器容器');
            return;
        }
        if (that.config.uploadPath === undefined) {
            layer.msg('Error:请配置图片上传地址');
            return;
        }
        $(that.config.editorEm).summernote({
            minHeight: that.config.minHeight,
            lang: that.config.lang,
            callbacks: {
                onImageUpload: function (files) {
                    that.upload(files)
                }
            }
        });
        return this;
    },
    upload: function (files) {
        var that = this, formData = new FormData();
        for (let f in files) {
            if (files[f].size > 1024*1024) {
                layer.msg("文件不能超过1MB!");
                return;
            }
            formData.append("file", files[f]);
        }
        $.ajax({
            url: that.config.uploadPath,
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (result) {
                if (result.code != 0) {
                    layer.msg(result.msg);
                    return;
                }
                for (let i in result.data) {
                    $(that.config.editorEm).summernote('insertImage', result.data[i], function ($image) {
                        $image.css('width', $image.width() / 2);
                        $image.attr('data-filename', 'retriever');
                    });
                }
            }
        });
    },
    getCode: function () {
        var code = $(this.config.editorEm).summernote('code');
        if (code === "<p><br></p>") {
            code = "";
        }
        return code;
    },
    setCode: function (code) {
        $(this.config.editorEm).summernote('code', code);
        return this;
    },
    destroy: function () {
        $(this.config.editorEm).summernote('destroy');
        return this;
    }
}
