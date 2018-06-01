package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Paramtype;
import com.supervisions.modules.sys.service.IParamtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * paramtype
 * @author cxh
 */
@Controller
@RequestMapping("/sys/paramtype")
public class ParamtypeController extends BaseController
{

    private String prefix = "sys/paramtype";

    @Autowired
    private IParamtypeService paramtypeService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String paramtype()
    {
        return prefix + "/paramtype";
    }

    /**
     * 新增 页面
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)
    {
        Paramtype paramtype = paramtypeService.selectParamtypeById(id);
        model.addAttribute("paramtype", paramtype);
        return prefix + "/edit";
    }

    /**
     * 详细 页面
     */
    @GetMapping("/detail/{parentId}")
    public String paramdata(@PathVariable("parentId") Long parentId, Model model)
    {
        model.addAttribute("parentId", parentId);
        return "sys/paramdata/paramdata";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Paramtype paramtype)
    {
        setPageInfo(paramtype);
        List<Paramtype> list = paramtypeService.selectParamtypeList(paramtype);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(Paramtype paramtype)
    {
        if (!paramtypeService.checkNameUnique(paramtype).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }
        if (paramtypeService.saveParamtype(paramtype) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 删除
     */
    @RequestMapping("/remove/{id}")
    @Transactional(rollbackFor=Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Long id)
    {
        Paramtype paramtype = paramtypeService.selectParamtypeById(id);
        if (paramtype == null)
        {
            return Message.error("数据不存在！");
        }
        if (paramtypeService.selectCountParamtypeByParentId(id) > 0)
        {
            return Message.error(1, "存在子数据,不允许删除");
        }
        if (paramtypeService.deleteParamtypeById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
