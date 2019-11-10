package run.app.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/30 10:19
 * Description:自定义配置文件
 */
@Data
@ConfigurationProperties("whoami")
public class AppProperties {
    private Boolean autoDel = false;

    private Boolean autoScan = true;

}
