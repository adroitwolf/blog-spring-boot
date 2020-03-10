package run.app.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 9:28
 * Description: :工程工具类
 */
@Component
public class AppUtil {

    private final static  SnowFlake snowFlake = new SnowFlake(0,0);

//    private static enum SingleEnum{
//        INSTANCE;
//        private AppUtil appUtil;
//
//        private SingleEnum(){
//            appUtil = new AppUtil();
//        }
//
//        public AppUtil getInstance(){
//            return appUtil;
//        }
//
//    }


    /**
    * 功能描述: 获取远程ip
    * @Author: WHOAMI
    * @Date: 2019/11/29 16:14
     */
    public static String  getRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
    * 功能描述: 流水生成自增id
    * @Author: WHOAMI
    * @Date: 2019/11/29 16:18
     */
    public static long nextId(){
        return snowFlake.nextId();
    }

    public static List<?> removeDuplicateListItem(List<?> items){
        return  new ArrayList<>(new HashSet<>(items));
    }

    public static String RandomUUIDWithoutDash(){
        return StringUtils.remove(UUID.randomUUID().toString(),"-");
    }

    public static String getCurrentData(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ss hh:mm:ss");
        return df.format(new Date());
    }


    /**
    * 功能描述: 生成6位验证码
    * @Param: []
    * @Return: java.lang.String
    * @Author: WHOAMI
    * @Date: 2020/3/10 12:43
     */
    public static String getCode(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i =0;i<6;i++){
            int randomInt = random.nextInt(10);
            builder.append(randomInt);
        }

        return builder.toString();
    }

}
