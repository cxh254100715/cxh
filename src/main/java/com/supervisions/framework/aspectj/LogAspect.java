package com.supervisions.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import com.supervisions.common.constant.UserConstants;
import com.supervisions.common.utils.ServletUtils;
import com.supervisions.common.utils.StringUtils;
import com.supervisions.common.utils.security.ShiroUtils;
import com.supervisions.framework.aspectj.lang.annotation.Log;
import com.supervisions.modules.sys.mapper.OperLog;
import com.supervisions.modules.sys.mapper.User;
import com.supervisions.modules.sys.service.IOperLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

/**
 * 操作日志记录处理
 */

@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private IOperLogService operLogService;

    // 配置织入点
    @Pointcut("@annotation(com.supervisions.framework.aspectj.lang.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint)
    {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    private void handleLog(JoinPoint joinPoint, Exception e)
    {
        try
        {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null)
            {
                return;
            }

            // 获取当前的用户
            User currentUser = ShiroUtils.getUser();

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(String.valueOf(UserConstants.NORMAL));
            // 请求的地址
            String ip = ShiroUtils.getIp();
            operLog.setIp(ip);
            operLog.setUrl(ServletUtils.getHttpServletRequest().getRequestURI());
            operLog.setCreateTime(new Date());
            if (currentUser != null)
            {
                operLog.setCreateUser(currentUser.getLoginName());
            }

            if (e != null)
            {
                operLog.setStatus(String.valueOf(UserConstants.EXCEPTION));
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 保存数据库
            operLogService.insertOperlog(operLog);
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 切点
     * @return 方法描述
     * @throws Exception
     */
    public static void getControllerMethodDescription(Log log, OperLog operLog) throws Exception
    {
        // 设置action动作
        operLog.setAction(log.action());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置channel
        operLog.setChannel(log.channel());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog
     */
    private static void setRequestValue(OperLog operLog)
    {
        Map<String, String[]> map = ServletUtils.getHttpServletRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setParam(StringUtils.substring(params, 0, 255));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private static Log getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
