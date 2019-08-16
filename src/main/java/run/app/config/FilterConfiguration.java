package run.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import run.app.config.properties.AppProperties;
import run.app.filter.LogFilter;
import run.app.security.filter.AuthenticationFilter;
import run.app.security.token.TokenService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/27 9:48
 * Description: ://TODO ${END}
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class FilterConfiguration {

    @Autowired
    AppProperties appProperties;

    @Bean
    public FilterRegistrationBean<LogFilter> initLogFilter(){
        FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return  filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> initCorsFilter(){
        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();

        corsFilter.setOrder(Ordered.LOWEST_PRECEDENCE);
        corsFilter.setFilter(new CorsFilter());

        corsFilter.addUrlPatterns("/*");

        return corsFilter;
    }


    @Bean
    public FilterRegistrationBean<AuthenticationFilter> initAuthenticationFilter(TokenService tokenService){


        FilterRegistrationBean<AuthenticationFilter> authenticationFilterFilter = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(tokenService);

        authenticationFilter.addExcludePatterns("/api/admin/login","/post/");

        authenticationFilterFilter.setFilter(authenticationFilter);

        authenticationFilterFilter.setOrder(Ordered.LOWEST_PRECEDENCE);

        authenticationFilterFilter.addUrlPatterns("/api/*");

        return authenticationFilterFilter;
    }


}
