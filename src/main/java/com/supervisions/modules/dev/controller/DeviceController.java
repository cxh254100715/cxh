package com.supervisions.modules.dev.controller;

import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.domain.Select;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.dev.service.IDeviceService;
import com.supervisions.modules.sys.mapper.Paramdata;
import com.supervisions.modules.sys.service.IParamdataService;
import com.supervisions.modules.sys.service.IParamtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * device
 * @author cxh
 */
@Controller
@RequestMapping("/dev/device")
public class DeviceController extends BaseController
{

    private String prefix = "dev/device";

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IParamtypeService paramtypeService;
    @Autowired
    private IParamdataService paramdataService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String device()
    {
        return prefix + "/device";
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
        Device device = deviceService.selectDeviceById(id);
        model.addAttribute("device", device);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Device device,String lalala)
    {
        setPageInfo(device);
        List<Device> list = deviceService.selectDeviceList(device);
        for(Device device1 : list){
            String str = device1.getParam();
            String strs = "";
            if (!StringUtils.isEmpty(str)) {
                String[] strArray = null;
                strArray = str.split(",");
                for (String id : strArray) {
                    if (StringUtils.isEmpty(id) || id.equals("null")) {
                        continue;
                    }
                    Paramdata param = paramdataService.selectParamdataById(Long.valueOf(id));
                    if (param != null) {
                        strs += param.getName() + ", ";
                    }
                }
                device1.setParam(strs.substring(0,strs.length()-2));
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
    public Message save(Device device)
    {
        if (!deviceService.checkNameUnique(device).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }
        if (deviceService.saveDevice(device) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 根据类型获取设备下拉数据
     */
    @RequestMapping("/getSelect")
    @ResponseBody
    public List<Select> getSelect(Integer type)
    {
        List<Device> list = deviceService.selectDeviceByType(type);
        List<Select> sLIst = new ArrayList<>();
        for(Device device : list){
            Select select = new Select(device.getId().toString(),device.getName());
            sLIst.add(select);
        }
        return sLIst;
    }

    /**
     * 删除
     */
    @RequestMapping("/remove/{id}")
    @Transactional(rollbackFor=Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Long id)
    {
        Device device = deviceService.selectDeviceById(id);
        if (device == null)
        {
            return Message.error("数据不存在！");
        }
        if (deviceService.selectCountDeviceinfoByDeviceId(id) > 0)
        {
            return Message.error(1, "数据已分配,不允许删除");
        }
        if (deviceService.deleteDeviceById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
