package com.supervisions.modules.dev.controller;

import com.supervisions.framework.aspectj.lang.annotation.Log;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.dev.mapper.Deviceinfo;
import com.supervisions.modules.dev.service.IDeviceinfoService;
import com.supervisions.modules.mer.mapper.Merchant;
import com.supervisions.modules.mer.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * deviceinfo
 * @author cxh
 */
@Controller
@RequestMapping("/dev/deviceinfo")
public class DeviceinfoController extends BaseController
{

    private String prefix = "dev/deviceinfo";

    @Autowired
    private IDeviceinfoService deviceinfoService;
    @Autowired
    private IMerchantService merchantService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String deviceinfo()
    {
        return prefix + "/deviceinfo";
    }

    /**
     * 新增 页面
     */
    @GetMapping("/add")
    public String add(Model model)
    {
        List<Merchant> merchants = merchantService.selectAllMerchants();
        model.addAttribute("merchants",merchants);
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)
    {
        Deviceinfo deviceinfo = deviceinfoService.selectDeviceinfoById(id);
        model.addAttribute("deviceinfo", deviceinfo);
        List<Merchant> merchants = merchantService.selectAllMerchants();
        model.addAttribute("merchants",merchants);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Deviceinfo deviceinfo)
    {
        setPageInfo(deviceinfo);
        List<Deviceinfo> list = deviceinfoService.selectDeviceinfoList(deviceinfo);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @Log(title = "设备管理", action = "设备管理-保存")
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(Deviceinfo deviceinfo)
    {
        if (!deviceinfoService.checkNameUnique(deviceinfo).equals("0"))
        {
            return Message.error(1,"序列号重复！");
        }
        if (deviceinfoService.saveDeviceinfo(deviceinfo) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 删除
     */
    @Log(title = "设备管理", action = "设备管理-删除")
    @RequestMapping("/remove/{id}")
    @Transactional(rollbackFor=Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Long id)
    {
        Deviceinfo deviceinfo = deviceinfoService.selectDeviceinfoById(id);
        if (deviceinfo == null)
        {
            return Message.error("数据不存在！");
        }
        if (deviceinfoService.deleteDeviceinfoById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
