package com.xula.entity.dict;


/**
 *
 * DictItem
 * 子类项值的子项
 */
public class DictItemValueItem {

    private String content ; // 子项 value 扩展 （内容）

    private String className; // 子项 value 扩展 （类名）

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
