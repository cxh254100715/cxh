package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.domain.Select;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.dev.mapper.Device;
import com.supervisions.modules.sys.mapper.Area;
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
 * area
 * @author cxh
 */
@Controller
@RequestMapping("/sys/area")
public class AreaController extends BaseController
{

    private String prefix = "sys/area";

    @Autowired
    private IAreaService areaService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String area()
    {
        return prefix + "/area";
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
        Area area = areaService.selectAreaById(id);
        if(area.getParentId()!=null&&area.getParentId()!=0){
            String parentName = areaService.selectAreaById(area.getParentId()).getName();
            area.setParentName(parentName);
        }
        model.addAttribute("area", area);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Area area)
    {
        setPageInfo(area);
        List<Area> list = areaService.selectAreaList(area);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * tree列表
     */
    @GetMapping("/list1")
    @ResponseBody
    public List<Area> list1(Long parentId)
    {
        List<Area> list = areaService.selectAreaList1(parentId);
        return list;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(Area area)
    {
        /*if (!areaService.checkNameUnique(area).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }*/
        if (areaService.saveArea(area) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 加载下拉列表树
     */
    @GetMapping("/areaTreeData")
    @ResponseBody
    public List<Map<String, Object>> areaTreeData()
    {
        List<Map<String, Object>> tree = areaService.areaTreeData();
        return tree;
    }

    /**
     * 根据父级id获取下拉数据
     */
    @RequestMapping("/getSelect")
    @ResponseBody
    public List<Select> getSelect(Long parentId)
    {
        List<Area> list = areaService.selectAreaByParentId(parentId);
        List<Select> sLIst = new ArrayList<>();
        for(Area area : list){
            Select select = new Select(area.getId().toString(),area.getName());
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
        Area area = areaService.selectAreaById(id);
        if (area == null)
        {
            return Message.error("数据不存在！");
        }
        if (areaService.deleteAreaById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
