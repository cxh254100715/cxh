package com.supervisions.modules.mer.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.domain.Select;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.mer.mapper.Merchant;
import com.supervisions.modules.mer.mapper.Merchantaddress;
import com.supervisions.modules.mer.service.IMerchantaddressService;
import com.supervisions.modules.sys.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * merchantaddress
 * @author cxh
 */
@Controller
@RequestMapping("/mer/merchantaddress")
public class MerchantaddressController extends BaseController
{

    private String prefix = "mer/merchantaddress";

    @Autowired
    private IMerchantaddressService merchantaddressService;
    @Autowired
    private IAreaService areaService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String merchantaddress(Model model, Long parentId)
    {
        model.addAttribute("parentId",parentId);
        return prefix + "/merchantaddress";
    }

    /**
     * 新增 页面
     */
    @GetMapping("/add")
    public String add(Model model, Long parentId)
    {
        model.addAttribute("merchantId",parentId);
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model)
    {
        Merchantaddress merchantaddress = merchantaddressService.selectMerchantaddressById(id);
        if(merchantaddress.getAreaId()!=null){
            Map<String,String> map = areaService.selectPCAById(merchantaddress.getAreaId());
            model.addAttribute("areas",map);
        }
        model.addAttribute("merchantaddress", merchantaddress);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Merchantaddress merchantaddress)
    {
        setPageInfo(merchantaddress);
        List<Merchantaddress> list = merchantaddressService.selectMerchantaddressList(merchantaddress);
        for(Merchantaddress merchantaddress1 : list){
            if(merchantaddress1.getAreaId()!=null) {
                Map<String,String> map = areaService.selectPCAById(merchantaddress1.getAreaId());
                String areaName = map.get("provinceName") + " " + map.get("cityName") + " " + map.get("areaName");
                merchantaddress1.setAreaName(areaName);
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
    public Message save(Merchantaddress merchantaddress)
    {
        if (!merchantaddressService.checkNameUnique(merchantaddress).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }
        if (merchantaddressService.saveMerchantaddress(merchantaddress) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 根据客户获取地址下拉数据
     */
    @RequestMapping("/getSelect")
    @ResponseBody
    public List<Select> getSelect(Long parentId)
    {
        List<Merchantaddress> list = merchantaddressService.selectMerchantaddressByParentId(parentId);
        List<Select> sLIst = new ArrayList<>();
        for(Merchantaddress merchantaddress : list){
            Select select = new Select(merchantaddress.getId().toString(),merchantaddress.getName());
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
        Merchantaddress merchantaddress = merchantaddressService.selectMerchantaddressById(id);
        if (merchantaddress == null)
        {
            return Message.error("数据不存在！");
        }
        if (merchantaddressService.selectCountDeviceinfoByMerchantaddressId(id) > 0)
        {
            return Message.error(1, "数据已分配,不允许删除");
        }
        if (merchantaddressService.deleteMerchantaddressById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
