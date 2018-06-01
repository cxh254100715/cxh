package com.supervisions.modules.sys.controller;

import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Menu;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.service.IMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController
{

    private String prefix = "sys/menu";

    @Autowired
    private IMenuService menuService;

    /**
     * 菜单 页面
     */
    @GetMapping()
    public String menu()
    {
        return prefix + "/menu";
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
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, Model model)
    {
        Menu menu = menuService.selectMenuById(menuId);
        if(menu.getParentId()!=null&&menu.getParentId()!=0){
            String parentName = menuService.selectMenuById(menu.getParentId()).getName();
            menu.setParentName(parentName);
        }
        model.addAttribute("menu", menu);
        return prefix + "/edit";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Menu menu)
    {
        setPageInfo(menu);
        List<Menu> list = menuService.selectMenuAll();
        for(Menu menu1 : list){
            if(menu1.getParentId()!=null&&menu1.getParentId()!=0){
                String parentName = menuService.selectMenuById(menu1.getParentId()).getName();
                menu1.setParentName(parentName);
            }
        }
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 删除菜单
    */
    @PostMapping("/remove/{menuId}")
    @ResponseBody
    public Message remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.selectCountMenuByParentId(menuId) > 0)
        {
            return Message.error(1, "存在子菜单,不允许删除");
        }
        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0)
        {
            return Message.error(1, "菜单已分配,不允许删除");
        }
        if (menuService.deleteMenuById(menuId) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 保存
    */
    @PostMapping("/save")
    @ResponseBody
    public Message save(Menu menu)
    {
        if (!menuService.checkMenuNameUnique(menu).equals("0"))
        {
            return Message.error(1,"菜单名重复！");
        }
        if (menuService.saveMenu(menu) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 加载角色菜单列表树
    */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Map<String, Object>> roleMenuTreeData(Role role)
    {
        List<Map<String, Object>> tree = menuService.roleMenuTreeData(role);
        return tree;
    }
    
    /**
     * 加载下拉菜单列表树
    */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Map<String, Object>> menuTreeData()
    {
        List<Map<String, Object>> tree = menuService.menuTreeData();
        return tree;
    }
}