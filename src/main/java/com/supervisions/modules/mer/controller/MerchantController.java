package com.supervisions.modules.mer.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.mer.mapper.Merchant;
import com.supervisions.modules.mer.service.IMerchantService;
import com.supervisions.modules.sys.dao.IAreaDao;
import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.sys.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * merchant
 * @author cxh
 */
@Controller
@RequestMapping("/mer/merchant")
public class MerchantController extends BaseController
{

    private String prefix = "mer/merchant";

    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IAreaService areaService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String merchant()
    {
        return prefix + "/merchant";
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
        Merchant merchant = merchantService.selectMerchantById(id);
        if(merchant.getAreaId()!=null){
            Map<String,String> map = areaService.selectPCAById(merchant.getAreaId());
            model.addAttribute("areas",map);
        }
        model.addAttribute("merchant", merchant);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Merchant merchant)
    {
        setPageInfo(merchant);
        List<Merchant> list = merchantService.selectMerchantList(merchant);
        for(Merchant merchant1 : list){
            if(merchant1.getAreaId()!=null) {
                Map<String,String> map = areaService.selectPCAById(merchant1.getAreaId());
                String areaName = map.get("provinceName") + " " + map.get("cityName") + " " + map.get("areaName");
                merchant1.setAreaName(areaName);
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
    public Message save(Merchant merchant)
    {
        if (!merchantService.checkNameUnique(merchant).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }
        if (merchantService.saveMerchant(merchant) > 0)
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
        Merchant merchant = merchantService.selectMerchantById(id);
        if (merchant == null)
        {
            return Message.error("数据不存在！");
        }
        if (merchantService.selectCountMerchantaddressByParentId(id) > 0)
        {
            return Message.error(1, "存在子数据,不允许删除");
        }
        if (merchantService.deleteMerchantById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
