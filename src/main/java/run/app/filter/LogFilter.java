package run.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/27 9:27
 * Description: 日志拦截器
 */
@Slf4j
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        String remoteAddr = httpServletRequest.getRemoteAddr();
        log.debug("");
        log.debug("ip:[{}] url:[{}]",remoteAddr,httpServletRequest.getRequestURL());
        filterChain.doFilter(httpServletRequest,httpServletResponse);


        log.debug("ip:[{}] url:[{}] using:[{}] ms",remoteAddr,httpServletRequest.getRequestURL(),(System.currentTimeMillis() - startTime));


    }
}
