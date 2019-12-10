package run.app.util;

import run.app.entity.DTO.ClickStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/28 22:30
 * Description: redis生成key
 */
public class RedisKeyUtil {



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
