package run.app.security.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerProfile;
import run.app.exception.AppException;
import run.app.exception.UnAuthenticationException;
import run.app.security.token.TokenService;
import run.app.security.token.impl.TokenServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 19:04
 * Description: ://TODO ${END}
 */
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> excludeUrlPatterns  = new HashSet<>();

    public AuthenticationFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authentication = httpServletRequest.getHeader("Authentication");


        if(shouldNotFilter(httpServletRequest)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }


        if(StringUtils.isEmpty(authentication)){
            handlerOnFailure(httpServletRequest,httpServletResponse, new UnAuthenticationException("用户未登录，请先登录！"));
            return;
        }




        if(!tokenService.verifierToken(authentication)){
            handlerOnFailure(httpServletRequest,httpServletResponse, new UnAuthenticationException("用户Token无效，请重新登陆！"));
            return;
        }

        if(tokenService.isExpire(authentication)){
            handlerOnFailure(httpServletRequest,httpServletResponse, new UnAuthenticationException("用户Token已经过期，请重新登录！"));
            return;
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }


    public boolean shouldNotFilter(HttpServletRequest httpServletRequest){
        return excludeUrlPatterns.stream().anyMatch(p->antPathMatcher.match(p,httpServletRequest.getServletPath()));
    }


    public void addExcludePatterns(String ...excludePattern){
        Collections.addAll(this.excludeUrlPatterns,excludePattern);
    }


    public void handlerOnFailure(HttpServletRequest request, HttpServletResponse response, AppException appException) throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        baseResponse.setMessage(appException.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        response.getWriter().write(JSONObject.toJSON(baseResponse).toString());
    }

}
