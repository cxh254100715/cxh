package com.supervisions.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.supervisions.common.constant.CommonConstant;
import com.supervisions.common.utils.FileUtils;
import com.supervisions.common.utils.ReadUtil;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.mapper.Versions;
import com.supervisions.modules.sys.service.IVersionsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app版本
 */
@Controller
@RequestMapping("/sys/versions")
public class VersionsController extends BaseController
{

    private static final Logger log = LoggerFactory.getLogger(VersionsController.class);

    private String prefix = "sys/versions";

    @Autowired
    private IVersionsService versionsService;

    /**
     * 版本更新 页面
     */
    @GetMapping()
    public String versions()
    {
        return prefix + "/versions";
    }

    /**
     * 新增
     */
    @GetMapping("/add")
    public String add(Model model)
    {
        return prefix + "/add";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(Versions versions)
    {
        setPageInfo(versions);
        List<Versions> list = versionsService.selectVersionsList(versions);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message save(Versions versions)
    {
        if(StringUtils.isEmpty(versions.getName())){
            return Message.error("请上传文件！");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("type",versions.getType());
        map.put("versionCode",versions.getVersionCode());
        if(versionsService.checkCodeUnique(map)!=0){
            return Message.error("版本已存在！");
        }

        if (versionsService.saveVersions(versions) > 0)
        {
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 删除
     */
    @RequestMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message remove(HttpServletRequest request, @PathVariable("id") Long id)
    {
        Versions versions = versionsService.selectVersionsById(id);
        if (versions == null)
        {
            return Message.error("数据不存在");
        }
        if (versionsService.deleteVersionsById(id) > 0)
        {
            String category = "";
            if(versions.getType()==0){
                category = "android";
            }else if(versions.getType()==1){
                category = "box";
            }
            String filePath = CommonConstant.UPLOADURL + "file//"+category+"//"+versions.getVersionCode()+"//";
            FileUtils.DeleteFolder(filePath);
            return Message.ok();
        }
        return Message.error();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public Message upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,String versionCode,Integer type)
    {
        if(StringUtils.isEmpty(versionCode)||versionCode.equals("undefined")){
            return Message.error("请输入版本号");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("versionCode",versionCode);
        if(versionsService.checkCodeUnique(map)!=0){
            return Message.error("版本已存在！");
        }

        String fileName = file.getOriginalFilename();
        /*String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!suffix.equals("apk")){
            return Message.error("请上传后缀为.apk文件");
        }*/
        //String serverPath = request.getSession().getServletContext().getRealPath("");
        //String parentPath = new File(serverPath).getParent();

        /*Map<String,Object> mapApk = ReadUtil.readAPK(file);
        if(!mapApk.get("package").equals(CommonConstant.PACKSGE)){
            //FileUtils.deleteFile(filePath+fileName);
            return Message.error("文件包名错误");
        }*/

        String category = "";
        if(type==0){
            category = "android";
        }else if(type==1){
            category = "box";
        }
        //String filePath = parentPath+"//ROOT//file//" + category + "//" + versionCode + "//";
        String filePath = CommonConstant.UPLOADURL + "file//" + category + "//" + versionCode + "//";

        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",fileName);
        jsonObject.put("url",CommonConstant.UPLOADHEADURL + "file/" + category + "/" + versionCode + "/" + fileName);
        //jsonObject.put("versionCode",mapApk.get("versionCode"));
        //jsonObject.put("packageName",mapApk.get("package"));
        return Message.ok().put("data",jsonObject);
    }

}
