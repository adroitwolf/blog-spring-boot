package run.app.listner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import run.app.config.properties.JWTProperties;
import run.app.service.TokenService;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/24 12:14
 * Description: :springboot容器初始化监听器
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)

public class StartListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    JWTProperties jwtProperties;

    @Autowired
    TokenService tokenService;



    @Value(value = "${web.upload-path}")
    private String imgUpload;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

//        初始化用户头像文件夹
        String filePath =imgUpload;

        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }

    }
}