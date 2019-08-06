package run.app.startLinster;

import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.app.security.token.TokenService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/24 12:14
 * Description: ://TODO ${END}
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartLinster implements ApplicationListener<ApplicationEvent> {

    @Autowired
    TokenService tokenService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        log.info("提前注入token");
    }
}
