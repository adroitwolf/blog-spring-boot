package run.app.util;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 12:10
 * Description: ://TODO ${END}
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate<String,String> redisTemplate;


    private Lock lock = new ReentrantLock();

    public  Boolean set(String key,String value){

        try{
            lock.lock();
            Optional<String> stringOptional = get(key);
            if(stringOptional.isPresent()){
                return false;
            }

        redisTemplate.opsForValue().set(key,value);
            return true;
        }finally {
            lock.unlock();
        }

    }


    public Boolean setWithTime(String key, String value, long timeout, TimeUnit timeUnit){
        try{
            lock.lock();
            Optional<String> stringOptional = get(key);
            if(stringOptional.isPresent()){
                return false;
            }

            redisTemplate.opsForValue().set(key,value,timeout,timeUnit);
            return true;
        }finally {
            lock.unlock();
        }
    }


    public Optional<String> get(String key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }


    public void remove(String key){
        try {
            lock.lock();
            redisTemplate.delete(key);
        }finally {
            lock.unlock();
        }
    }


}
