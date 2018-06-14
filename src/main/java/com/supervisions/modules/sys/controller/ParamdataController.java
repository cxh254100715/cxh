package com.supervisions.modules.sys.controller;

import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Paramdata;
import com.supervisions.modules.sys.mapper.Paramtype;
import com.supervisions.modules.sys.service.IParamdataService;
import com.supervisions.modules.sys.service.IParamtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * paramdata
 * @author cxh
 */
@Controller
@RequestMapping("/sys/paramdata")
public class ParamdataController extends BaseController
{

    private String prefix = "sys/paramdata";

    @Autowired
    private IParamdataService paramdataService;
    @Autowired
    private IParamtypeService paramtypeService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String paramdata()
    {
        return prefix + "/paramdata";
    }

    /**
     * 新增 页面
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, Model model)
    {
        model.addAttribute("parentId", parentId);
        return prefix + "/add";
    }

    /**
     * 编辑 页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Long parentId, Model model)
    {
        Paramdata paramdata = paramdataService.selectParamdataById(id);
        model.addAttribute("parentId", parentId);
        model.addAttribute("paramdata", paramdata);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Paramdata paramdata)
    {
        setPageInfo(paramdata);
        List<Paramdata> list = paramdataService.selectParamdataList(paramdata);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(Paramdata paramdata)
    {
        if (!paramdataService.checkNameUnique(paramdata).equals("0"))
        {
            return Message.error(1,"名称重复！");
        }
        if (paramdataService.saveParamdata(paramdata) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 根据type获取下拉框数据
     */
    @RequestMapping("/getSelectByType")
    @ResponseBody
    public Message getSelectByType(Integer type,Model model)
    {
        if(type!=null){
            List<Paramtype> list = paramtypeService.selectParamtypeByType(type);

            List<Map<String, Object>> list3 = new ArrayList<>();
            for(Paramtype paramtype : list){
                Map<String, Object> map = new HashMap<>();
                map.put("name",paramtype.getName());
                map.put("code",paramtype.getCode());
                List<Paramdata> list1 = paramdataService.selectParamdataByParamId(paramtype.getId());
                List<Map<String, Object>> list2 = new ArrayList<>();
                for(Paramdata paramdata : list1){
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("text",paramdata.getName());
                    map1.put("value",paramdata.getId());
                    list2.add(map1);
                }
                map.put("datas",list2);
                list3.add(map);
            }
            //model.addAttribute("params",map);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("datas",list3);
            return Message.ok(map1);
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
        Paramdata paramdata = paramdataService.selectParamdataById(id);
        if (paramdata == null)
        {
            return Message.error("数据不存在！");
        }
        if (paramdataService.selectCountDeviceByParamdataId(id) > 0)
        {
            return Message.error(1, "数据已分配,不允许删除");
        }
        if (paramdataService.deleteParamdataById(id) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

}
