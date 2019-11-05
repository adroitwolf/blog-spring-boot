package run.app.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 18:10
 * Description: jwt配置文件
 */
@Data
@Component
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    /**
     * 发行者名
     */
    private String name;

    /**
     * base64加密密钥
     */
    private String base64Secret;

    /**
     * jwt中过期时间设置(s)
     */
    private long jwtExpires;

}
