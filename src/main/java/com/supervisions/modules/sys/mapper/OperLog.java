package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

import java.util.Date;

/**
 * 操作日志记录 oper_log
 */
public class OperLog extends PageDomain
{
    /** 模块标题 */
    private String title;
    /** 功能请求 */
    private String action;
    /** 请求方法 */
    private String method;
    /** 来源渠道 */
    private String channel;
    /** 部门名称 */
    private String deptName;
    /** 请求url */
    private String url;
    /** 操作地址 */
    private String ip;
    /** 请求参数 */
    private String param;
    /** 错误消息 */
    private String errorMsg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
