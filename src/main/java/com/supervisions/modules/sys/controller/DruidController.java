package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * druid 监控
 */
@Controller
@RequestMapping("/sys/data")
public class DruidController extends BaseController
{

    private String prefix = "/sys/druid";

    //@RequiresPermissions("monitor:data:view")
    @GetMapping()
    public String index()
    {
        return "redirect:" + prefix + "/index";
    }
}
