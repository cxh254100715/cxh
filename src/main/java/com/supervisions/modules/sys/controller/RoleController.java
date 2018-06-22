package com.supervisions.modules.sys.controller;

import com.supervisions.framework.aspectj.lang.annotation.Log;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController
{

    private String prefix = "sys/role";

    @Autowired
    private IRoleService roleService;

    /**
     * 角色 页面
     */
    @GetMapping()
    public String role()
    {
        return prefix + "/role";
    }

    /**
     * 新增 页面
     */
    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)
    {
        Role role = roleService.selectRoleById(id);
        model.addAttribute("role", role);
        return prefix + "/edit";
    }

    /**
     * 设置菜单 页面
     */
    @GetMapping("/setupMenu/{id}")
    public String setupMenu(@PathVariable("id") Long id, Model model)
    {
        Role role = roleService.selectRoleById(id);
        model.addAttribute("role", role);
        return prefix + "/setupMenu";
    }

    /**
     * 列表
     */
    @RequiresPermissions("sys:role:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Role role)
    {
        setPageInfo(role);
        List<Role> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /**
     * 保存
     */
    @Log(title = "系统管理", action = "角色管理-保存角色")
    @PostMapping("/save")
    @Transactional(rollbackFor=Exception.class)
    @ResponseBody
    public Message save(Role role)
    {
        if (!roleService.checkRoleNameUnique(role).equals("0"))
        {
            return Message.error(1,"角色名重复！");
        }
        if (roleService.saveRole(role) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }


    /**
     * 设置菜单
     */
    @PostMapping("/saveMenus")
    @Transactional(rollbackFor=Exception.class)
    @ResponseBody
    public Message saveMenus(Role role)
    {
        if (roleService.insertRoleMenu(role) > 0)
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
        Role role = roleService.selectRoleById(id);
        if (role == null)
        {
            return Message.error("角色不存在");
        }
        if (roleService.selectCountUserRoleByRoleId(id) > 0)
        {
            return Message.error("角色已分配,不能删除");
        }
        if (roleService.deleteRoleById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}