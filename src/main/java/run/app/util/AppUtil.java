package run.app.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


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


    private static enum SingleEnum{
        INSTANCE;
        private AppUtil appUtil;

        private SingleEnum(){
            appUtil = new AppUtil();
        }

        public AppUtil getInstance(){
            return appUtil;
        }

    }

//    获取自增id
    public long nextId(){
        return snowFlake.nextId();
    }


    public List<?> removeDuplicateListItem(List<?> items){
        return  new ArrayList<>(new HashSet<>(items));
    }

    public static AppUtil getInstance(){
        return SingleEnum.INSTANCE.getInstance();
    }

    public static String RandomUUIDWithoutDash(){
        return StringUtils.remove(UUID.randomUUID().toString(),"-");
    }

    public static String getCurrentData(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ss hh:mm:ss");
        return df.format(new Date());
    }


}
