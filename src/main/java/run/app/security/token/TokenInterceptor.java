package run.app.security.token;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import run.app.service.TokenService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 17:23
 * Description: :token的拦截认证
 */
@Slf4j
@Configuration
@Aspect
public class TokenInterceptor {

    @Autowired
    TokenService tokenService;


    @Around("@annotation(run.app.security.token.AuthTokenInterface)")
    public Object interceptorAuth(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest = attributes.getRequest();

        String authentication = servletRequest.getHeader("Authentication");

        Signature signature = joinPoint.getSignature();


        return joinPoint.proceed();
    }

}
