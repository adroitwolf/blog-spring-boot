package run.app.security.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.entity.model.BlogLog;
import run.app.security.token.TokenService;
import run.app.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 9:32
 * Description: ://TODO ${END}
 */
@Aspect
@Slf4j
@Component
public class LogManager {

//    @Autowired
//    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    LogService logService;

    @Around("@annotation(run.app.security.log.MethodLog)")
    public Object storage(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String ip = getIp(request);
        BlogLog blogLog = new BlogLog();

        String token = request.getHeader("Authentication");
//        blogLog.setUsername(tokenService.getUsernameByToken(token));
        blogLog.setUsername(userService.getUsernameByToken(token));
        blogLog.setRomoteip(ip);
        blogLog.setRomotetime(new Date());
        blogLog.setOperation(targetName+"."+methodName);
        logService.storageLog(blogLog);

        return joinPoint.proceed();
    }

    public  String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
