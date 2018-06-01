package com.supervisions.framework.web.domain;

import java.io.Serializable;

public class Select implements Serializable
{

    /**
     * 值域
     */
    private String value;
    /**
     * 文本域
     */
    private String text;
    /**
     * 分组
     */
    private String group;
    /**
     * 是否选中
     */
    private boolean selected;

    /**
     * 其它数据
     */
    private Object data;

    public Select() {
        super();
    }

    /**
     *
     * @param value
     *            值域
     * @param text
     *            文本域
     */
    public Select(String value, String text) {
        super();
        this.value = value;
        this.text = text;
    }

    /**
     *
     * @param value
     *            值域
     * @param text
     *            文本域
     * @param group
     *            分组
     */
    public Select(String value, String text, String group) {
        super();
        this.value = value;
        this.text = text;
        this.group = group;
    }

    /**
     *
     * @param value
     *            值域
     * @param text
     *            文本域
     * @param group
     *            分组
     * @param selected
     *            是否选中
     */
    public Select(String value, String text, String group, boolean selected) {
        super();
        this.value = value;
        this.text = text;
        this.group = group;
        this.selected = selected;
    }

    /**
     * 值域
     */
    public String getValue() {
        return value;
    }
    /**
     * 设置值域
     * @param value   文本域
     */
    public Select setValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * 文本域
     */
    public String getText() {
        return text;
    }

    /**
     * 设置文本域
     * @param text   文本域
     */
    public Select setText(String text) {
        this.text = text;
        return this;
    }


    /**
     * 分组
     * @return
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置分组
     * @param group
     */
    public Select setGroup(String group) {
        this.group = group;
        return this;
    }

    /**
     * 是否选中
     */
    public boolean isSelected() {
        return selected;
    }

    public Select setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }


    /**
     * 其它数据
     * @return
     */
    public Object getData() {
        return data;
    }
    /**
     * 设置其它数据
     * @return
     */
    public Select setData(Object data) {
        this.data = data;
        return this;
    }
}
