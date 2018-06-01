package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.modules.sys.mapper.Menu;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页 业务处理

 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;


    // 系统首页
    @GetMapping("/index")
    public String index(Model model)
    {
        // 取身份信息
        User user = getUser();
        // 根据用户id取出菜单
        List<Menu> menus = new ArrayList<>();
        if(user.getId()==1){
            menus = menuService.selectMenusByAdmin();
        }else{
            menus = menuService.selectMenusByUserId(user.getId());
        }
        model.addAttribute("menus", menus);
        model.addAttribute("user", user);
        return "index";
    }

    // 系统介绍
    @GetMapping("/sys/main")
    public String main(Model model)
    {
        return "main";
    }

}
