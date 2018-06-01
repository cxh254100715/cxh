package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Logininfor;
import com.supervisions.modules.sys.service.ILogininforService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 */
@Controller
@RequestMapping("/sys/logininfor")
public class LogininforController extends BaseController
{
    private String prefix = "sys/logininfor";

    @Autowired
    private ILogininforService logininforService;

    /**
     * 登录日志 页面
     */
    @GetMapping()
    public String logininfor()
    {
        return prefix + "/logininfor";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Logininfor logininfor)
    {
        setPageInfo(logininfor);
        List<Logininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }
}
