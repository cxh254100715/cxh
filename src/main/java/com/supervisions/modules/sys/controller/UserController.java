package com.supervisions.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.supervisions.common.constant.CommonConstant;
import com.supervisions.common.utils.FileUtils;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.framework.aspectj.lang.annotation.Log;
import com.supervisions.framework.shiro.service.PasswordService;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.framework.web.service.RedisService;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.service.IRoleService;
import com.supervisions.modules.sys.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 用户信息
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController
{

    private String prefix = "sys/user";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordService passwordService;

    /**
     * 用户 页面
     */
    @GetMapping()
    public String user(Model model)
    {
        Long userId = ShiroUtils.getUserId();
        model.addAttribute("sessionUserId",userId);
        return prefix + "/user";
    }

    /**
     * 新增 页面
     */
    @GetMapping("/add")
    public String add(Model model)
    {
        List<Role> roles = roleService.selectRolesAll();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)
    {
        User user = userService.selectUserById(id);
        List<Role> roles = roleService.selectRolesAll();
        List<Role> roles1 = roleService.selectRolesByUserId(id);
        model.addAttribute("roles", roles);
        if(roles1!=null&&roles1.size()>0){
            Long[] roleIds = new Long[roles1.size()];
            for (int i = 0; i < roles1.size(); i++) {
                roleIds[i] = roles1.get(i).getId();
            }
            model.addAttribute("roleIds", roleIds);
        }
        model.addAttribute("user", user);
        return prefix + "/edit";
    }

    /**
     * 个人信息 页面
     */
    @GetMapping("/profile")
    public String profile(Model model)
    {
        User user = userService.selectUserById(ShiroUtils.getUserId());
        String roleNames = "";
        List<Role> roles = roleService.selectRolesByUserId(user.getId());
        if(roles!=null && roles.size()>0){
            for (int i = 0; i < roles.size(); i++) {
                String roleName = roleService.selectRoleById(roles.get(i).getId()).getName();
                roleNames += roleName + ",";
            }
            model.addAttribute("roleNames", roleNames.substring(0,roleNames.length()-1));
        }

        model.addAttribute("user", user);
        return prefix + "/profile/profile";
    }

    /**
     * 重置密码 页面
     */
    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, Model model)
    {
        User user = userService.selectUserById(id);
        model.addAttribute("user", user);
        return prefix + "/resetPwd";
    }

    /**
     * 修改密码 页面
     */
    @GetMapping("/updatePwdPage")
    public String updatePwd(Model model)
    {
        User user = userService.selectUserById(ShiroUtils.getUserId());
        model.addAttribute("user", user);
        return prefix + "/profile/resetPwd";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user)
    {
        setPageInfo(user);
        List<User> list = userService.selectUserList(user);
        for(User entity : list){
            String roleNames = "";
            List<Role> roles = roleService.selectRolesByUserId(entity.getId());
            if(roles!=null && roles.size()>0){
                for(Role role : roles){
                    String roleName = roleService.selectRoleById(role.getId()).getName();
                    roleNames += roleName + ",";
                }
                entity.setRoleNames(roleNames.substring(0,roleNames.length()-1));
            }
        }
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(User user)
    {
        if (!userService.checkUserNameUnique(user).equals("0"))
        {
            return Message.error(1,"用户名重复！");
        }
        if (userService.saveUser(user) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 修改个人信息
     */
    @PostMapping("/updateProfile")
    @ResponseBody
    public Message updateProfile(User user)
    {
        int rows = userService.updateProfile(user);
        if (rows > 0)
        {
            ShiroUtils.setUser(userService.selectUserById(user.getId()));
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    @ResponseBody
    public Message resetPwd(User user)
    {
        int rows = userService.resetUserPwd(user);
        if (rows > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    @ResponseBody
    public Message updatePwd(Long id,String oldPassword,String password, Model model)
    {
        User user = userService.selectUserById(id);
        String comPassword = passwordService.encryptPassword(user.getLoginName(), oldPassword, user.getSalt());
        if(!comPassword.equals(user.getPassword())){
            return Message.error("原密码错误！");
        }
        user.setPassword(password);
        int rows = userService.resetUserPwd(user);
        if (rows > 0)
        {
            ShiroUtils.logout();
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
        User user = userService.selectUserById(id);
        if (user == null)
        {
            return Message.error("用户不存在");
        }

        if (userService.deleteUserById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    @ResponseBody
    public Message upload(@RequestParam("file") MultipartFile file)
    {
        String filePath = CommonConstant.UPLOADURL + "images/profile/pc/";
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Random random = new Random();
        Integer randomNum = random.nextInt(9999)%(9999-1000+1) + 1000;
        String fileName = System.currentTimeMillis() + randomNum.toString() + "." + suffix; // 时间戳+四位随机数
        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.getMessage();
        }

        return Message.ok().put("icon",CommonConstant.UPLOADHEADURL + "images/profile/pc/" + fileName);
    }

}