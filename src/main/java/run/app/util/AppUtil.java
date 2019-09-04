package run.app.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 9:28
 * Description: :工程工具类
 */
@Component
public class AppUtil {

    public static String RandomUUIDWithoutDash(){
        return StringUtils.remove(UUID.randomUUID().toString(),"-");
    }

    public static String getCurrentData(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ss hh:mm:ss");
        return df.format(new Date());
    }


}
