package com.supervisions.modules.ten.controller;

import com.supervisions.common.constant.CommonConstant;
import com.supervisions.common.utils.FileUtils;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.Area;
import com.supervisions.modules.sys.mapper.Role;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.ten.mapper.TenPicture;
import com.supervisions.modules.ten.service.ITenPictureService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

/**
 * tenPicture
 * @author cxh
 */
@Controller
@RequestMapping("/ten/picture")
public class TenPictureController extends BaseController
{
    private String prefix = "ten/picture";

    @Autowired
    private ITenPictureService tenPictureService;

    /**
     * 列表 页面
     */
    @GetMapping()
    public String picture(){
        return prefix + "/picture";
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
        TenPicture tenPicture = tenPictureService.selectTenPictureById(id);
        model.addAttribute("picture", tenPicture);
        return prefix + "/edit";
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(TenPicture picture)
    {
        setPageInfo(picture);
        List<TenPicture> list = tenPictureService.selectPictureList(picture);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(TenPicture picture)
    {
        if(StringUtils.isEmpty(picture.getUrl())){
            return Message.error("请上传图片！");
        }
        if (tenPictureService.savePicture(picture) != null)
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
    public Message remove(@PathVariable("id") Long id) throws Exception
    {
        TenPicture picture = tenPictureService.selectTenPictureById(id);
        if (picture == null)
        {
            return Message.error("数据不存在");
        }
        if (tenPictureService.deleteTenPictureById(id) != null)
        {
            if(picture.getUrl().indexOf("images/profile/app/")==-1){
                throw new Exception("数据异常");
            }
            String fileName = picture.getUrl().split("images/profile/app/")[1];
            String filePath = CommonConstant.UPLOADURL + "images/profile/app/" + fileName;
            FileUtils.DeleteFolder(filePath);
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
        String filePath = CommonConstant.UPLOADURL + "images/profile/app/";
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Random random = new Random();
        Integer randomNum = random.nextInt(9999)%(9999-1000+1) + 1000;
        String fileName = System.currentTimeMillis() + randomNum.toString() + "." + suffix; // 时间戳+四位随机数
        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.getMessage();
        }

        return Message.ok().put("url",CommonConstant.UPLOADHEADURL + "images/profile/app/" + fileName);
    }

}
