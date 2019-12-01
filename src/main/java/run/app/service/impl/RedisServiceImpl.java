package run.app.service.impl;

import io.micrometer.core.instrument.internal.DefaultGauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.ClickStatus;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.model.BlogStatus;
import run.app.service.RedisService;
import run.app.util.RedisKeyUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/20 17:33
 * Description:redis服务层实现类
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    private final String LocalKey = "LOCAL_KEY";

    private final String LocalValue = "LOCAL_VALUE";

    private static final String BLOG_CLICKED_KEY="BLOG_CLICKED";

    private static final String LIST_TOP5_POSTS = "TOP5_POSTS";

    @Override
    public void incrementBlogClickedCount(ClickStatus clickStatus) {
        Boolean lock = getLock();
        if(!lock){
            log.info("redis正在添加缓存...请稍等"); //此时锁有人占用
        }

        try{
            String key = RedisKeyUtil.getClickSetKey(clickStatus);
            if(redisTemplate.opsForSet().isMember(key,clickStatus.getBlogId())){
                return;
            }
            redisTemplate.opsForHash().increment(BLOG_CLICKED_KEY,clickStatus.getBlogId(),clickStatus.getCount());

            redisTemplate.opsForSet().add(key,clickStatus.getBlogId());
            redisTemplate.expire(key,1,TimeUnit.DAYS); //过期时间为1天
        }finally {
            redisTemplate.delete(LocalKey);
        }
    }

    @Override
    public Integer getBlogClickedCount(Long blogId) {
        return (Integer) redisTemplate.opsForHash().get(BLOG_CLICKED_KEY,blogId);
    }

    @Override
    public List<BlogStatus> listBlogClickedCounts() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(BLOG_CLICKED_KEY, ScanOptions.NONE);

        List<BlogStatus> list = new ArrayList<>();

        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry =  cursor.next();
            Long  blogId = (Long)entry.getKey();
            Integer count = (Integer) entry.getValue();

            BlogStatus status = new BlogStatus(blogId, count);

            list.add(status);
            redisTemplate.opsForHash().delete(BLOG_CLICKED_KEY,blogId);
        }
        return list;
    }

    @Override
    public void transTop5Posts2Redis(List<PopularBlog> list) {
        Boolean lock = getLock();
        if(!lock){
            log.info("redis正在添加缓存...请稍等"); //此时锁有人占用
        }
        try{
            //先增，然后删除，避免在更新的时候有人访问
            list.stream().filter(Objects::nonNull).forEach(entity->
                redisTemplate.opsForZSet().add(LIST_TOP5_POSTS,entity,entity.getClickcount())
            );
            Long size = redisTemplate.opsForZSet().size(LIST_TOP5_POSTS);
            if(size>5){
                Long end = size - 6;
                redisTemplate.opsForZSet().removeRange(LIST_TOP5_POSTS, 0, end);
            }

        }finally {
            redisTemplate.delete(lock);
        }
    }

    @Override
    public Set<PopularBlog> listTop5FrmRedis() {
        Set<PopularBlog> set = redisTemplate.opsForZSet().reverseRange(LIST_TOP5_POSTS, 0, 5);
        return set;
    }


    //    获取redis锁
    private Boolean getLock(){
        return redisTemplate.opsForValue().setIfAbsent(LocalKey, LocalValue, 2, TimeUnit.SECONDS);
    }
}
