package com.xula.base.log;

import com.alibaba.fastjson.JSON;
import com.xula.base.utils.ReqUtils;
import com.xula.base.utils.ShiroUtils;
import com.xula.entity.SysOperatorLog;
import com.xula.service.sys.log.ISysOperatorLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志操作aop实现
 * @author xla
 */
@Aspect
@Component
public class LogInterceptor {
    @Pointcut("execution(* com.xula.service.*.*.*(..)) && @annotation(com.xula.base.log.Log)")
    private void anyMethod(){}//定义一个切入点

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysOperatorLogService iSysOperatorLogService;

    @AfterReturning(value = "@annotation(log)", argNames = "log")
    public void doAccessCheck(JoinPoint joinPoint, Log log){
        // 判断参数
        if (joinPoint.getArgs() == null) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        SysOperatorLog sysOperatorLog = new SysOperatorLog();
        sysOperatorLog.setOperation(log.operateName());
        sysOperatorLog.setIp(ReqUtils.getIp(request));
        sysOperatorLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);

        sysOperatorLog.setParams(params);
        sysOperatorLog.setSysName(ShiroUtils.getUserName());
        sysOperatorLog.setSysUid(ShiroUtils.getUserId());
        sysOperatorLog.setCreateTime(new Date());
        //保存系统日志
        iSysOperatorLogService.addOperatorLog(sysOperatorLog);
    }
}
