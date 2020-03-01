package run.app.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/19 13:15
 * Description: xss过滤器
 */
public class XssFilter extends OncePerRequestFilter {

    //是否过滤富文本内容
    private static boolean IS_INCLUDE_RICH_TEXT = true;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpServletRequest, IS_INCLUDE_RICH_TEXT);
        filterChain.doFilter(xssRequest, httpServletResponse);
    }
}
