package com.supervisions.modules.sys.mapper;

import com.supervisions.framework.web.page.PageDomain;

import java.util.Date;

/**
 * 系统访问日志情况信息 sys_logininfor
 */
public class Logininfor extends PageDomain
{
    /** 登录IP地址 */
    private String ip;
    /** 浏览器类型 */
    private String browser;
    /** 操作系统 */
    private String os;
    /** 提示消息 */
    private String msg;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}