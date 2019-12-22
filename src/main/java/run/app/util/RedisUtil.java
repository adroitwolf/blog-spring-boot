package run.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import run.app.entity.DTO.ClickStatus;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/28 22:30
 * Description: redis相关工具类
 */
@Component
public class RedisUtil {



    /**
    * 功能描述: 通过客户端生成文章点击量的key值
    * @Author: WHOAMI
    * @Date: 2019/11/29 17:00
     */
    public static String getClickSetKey(ClickStatus clickStatus){
        StringBuilder builder = new StringBuilder();
        builder.append(clickStatus.getSessionId());
        builder.append("-");
        builder.append(clickStatus.getRemoteIp());

        return builder.toString();
    }


}
