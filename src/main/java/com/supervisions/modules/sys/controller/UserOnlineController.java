package com.supervisions.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.framework.shiro.session.OnlineSessionDAO;
import com.supervisions.framework.web.controller.BaseController;
import com.supervisions.framework.web.domain.Message;
import com.supervisions.framework.web.page.PageDomain;
import com.supervisions.framework.web.page.PageDomain1;
import com.supervisions.framework.web.page.TableDataInfo;
import com.supervisions.modules.sys.mapper.OnlineSession;
import com.supervisions.modules.sys.mapper.UserOnline;
import com.supervisions.modules.sys.service.IUserOnlineService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 在线用户监控
 */
@Controller
@RequestMapping("/sys/online")
public class UserOnlineController extends BaseController
{
    private String prefix = "sys/online";

    @Autowired
    private IUserOnlineService userOnlineService;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 在线用户 页面
     */
    @GetMapping()
    public String online()
    {
        return prefix + "/online";
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserOnline userOnline)
    {
        //setPageInfo(userOnline);
        PageDomain1 page = (PageDomain1) userOnline;
        if (StringUtils.isNotEmpty(page.getPage()) && StringUtils.isNotEmpty(page.getLimit()))
        {
            int pageNum = Integer.valueOf(page.getPage());
            int pageSize = Integer.valueOf(page.getLimit());
            String orderBy = page.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        List<UserOnline> list = userOnlineService.selectUserOnlineList(userOnline);
        return getDataTable(list);
    }

    /*@RequiresPermissions("monitor:online:batchForceLogout")
    @Log(title = "监控管理", action = "在线用户-批量强退用户")
    @PostMapping("/batchForceLogout")
    @ResponseBody
    public Message batchForceLogout(@RequestParam("ids[]") String[] ids)
    {
        for (String sessionId : ids)
        {
            UserOnline online = userOnlineService.selectOnlineById(sessionId);
            if (online == null)
            {
                return Message.error("用户已下线");
            }
            OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
            if (onlineSession == null)
            {
                return Message.error("用户已下线");
            }
            onlineSession.setStatus(OnlineSession.OnlineStatus.off_line);
            online.setStatus(OnlineSession.OnlineStatus.off_line);
            userOnlineService.saveOnline(online);
        }
        return Message.ok();
    }*/

    /**
     * 下线
     */
    @RequestMapping("/forceLogout/{sessionId}")
    @ResponseBody
    public Message forceLogout(@PathVariable("sessionId") String sessionId)
    {
        UserOnline online = userOnlineService.selectOnlineById(sessionId);
        if (online == null)
        {
            return Message.error("用户已下线");
        }
        if (online.getStatus().getInfo().equals("离线"))
        {
            return Message.error("用户已下线");
        }
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
        if (onlineSession == null)
        {
            return Message.error("用户已下线");
        }
        onlineSession.setStatus(OnlineSession.OnlineStatus.off_line);
        online.setStatus(OnlineSession.OnlineStatus.off_line);
        userOnlineService.saveOnline(online);
        return Message.ok();
    }

}
