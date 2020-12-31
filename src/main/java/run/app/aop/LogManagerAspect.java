package run.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.entity.model.BlogLog;
import run.app.service.AccountService;
import run.app.service.LogService;
import run.app.util.AppUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 9:32
 * Description: 日志管理器
 */
@Aspect
@Slf4j
@Component
public class LogManagerAspect {


    @Autowired
    AccountService accountService;

    @Autowired
    LogService logService;

    @Around("@annotation(run.app.aop.annotation.MethodLog)")
    public Object storage(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String ip = AppUtil.getRemoteIp(request);
        BlogLog blogLog = new BlogLog();

        String token = request.getHeader("Authentication");
//        blogLog.setUsername(tokenService.getUsernameByToken(token));
        blogLog.setUsername(accountService.getEmailByToken(token));
        blogLog.setRomoteip(ip);
        blogLog.setRomotetime(new Date());
        blogLog.setOperation(targetName + "." + methodName);
        logService.storageLog(blogLog);

        return joinPoint.proceed();
    }
}
