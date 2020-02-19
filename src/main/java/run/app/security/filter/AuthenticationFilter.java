package run.app.security.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.enums.RoleEnum;
import run.app.exception.AppException;
import run.app.exception.UnAccessException;
import run.app.exception.UnAuthenticationException;
import run.app.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 19:04
 * Description: :用户权限过滤器
 */
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> excludeUrlPatterns  = new HashSet<>();

    private Map<RoleEnum,List<String>> authorityPatterns = new HashMap<>();


    public AuthenticationFilter(TokenService tokenService){
        this.tokenService = tokenService;
    }


    /**
    * 功能描述: 取代spring security的认证功能 单体应用下的认证
    * @Return: void
    * @Author: WHOAMI
    * @Date: 2020/2/15 12:47
     */
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


//        验证用户角色
        if(verifyAuthority(tokenService.getRoles(authentication),httpServletRequest)){
            handlerOnFailure(httpServletRequest,httpServletResponse,new UnAccessException("用户没有权限"));
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


        /**
        * 功能描述: 判断过滤  false表示有权限
        * @Param: [roles, request]
        * @Return: boolean
        * @Author: WHOAMI
        * @Date: 2020/2/15 12:47
         */
    private boolean verifyAuthority(List<RoleEnum> roles, HttpServletRequest request){
            return roles.stream()
                    .filter(p->
                        authorityPatterns.get(p)
                                .stream().anyMatch(x-> antPathMatcher
                                .match(x,request.getServletPath()))).count()>0?false:true;
    }

    /**
    * 功能描述: 添加权限控制
     * 这里由于功能简单，只有管理员和用户，所以权限应该分别对待
     */
    public void addAuthorityPatterns(Map<RoleEnum,List<String>>url){
        this.authorityPatterns = url;
    }

    public void handlerOnFailure(HttpServletRequest request, HttpServletResponse response, AppException appException) throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(appException.getStatus().value());
        baseResponse.setMessage(appException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSONObject.toJSON(baseResponse).toString());
    }

}
