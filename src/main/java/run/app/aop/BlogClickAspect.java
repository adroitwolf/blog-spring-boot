package run.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.entity.DTO.ClickStatus;
import run.app.service.RedisService;
import run.app.util.AppUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 16:27
 * Description: 文章点击量切面处理器
 */
@Component
@Aspect
@Slf4j
public class BlogClickAspect {

    @Autowired
    RedisService redisService;


    @Around("@annotation(run.app.aop.annotation.IncrementClickCount)")
    public Object incrementBlogClick(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String remoteIp = AppUtil.getRemoteIp(request);
        String sessionId = request.getRequestedSessionId();

        Object[] args = joinPoint.getArgs();

        Long blogId = (Long) args[0];

        ClickStatus clickStatus = new ClickStatus();
        clickStatus.setBlogId(blogId);
        clickStatus.setCount(1);
        clickStatus.setRemoteIp(remoteIp);
        clickStatus.setSessionId(sessionId);
        redisService.incrementBlogClickedCount(clickStatus);
        return joinPoint.proceed();
    }

}
