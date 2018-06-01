package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.OperLog;
import com.supervisions.modules.sys.service.IOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 */
@Controller
@RequestMapping("/sys/operlog")
public class OperlogController extends BaseController
{
    private String prefix = "sys/operlog";

    @Autowired
    private IOperLogService operLogService;

    /**
     * 操作日志 页面
     */
    @GetMapping()
    public String operlog()
    {
        return prefix + "/operlog";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(OperLog operLog)
    {
        setPageInfo(operLog);
        List<OperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    /**
     * 详细 页面
     */
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long deptId, Model model)
    {
        OperLog operLog = operLogService.selectOperLogById(deptId);
        model.addAttribute("operLog", operLog);
        return prefix + "/detail";
    }
}
