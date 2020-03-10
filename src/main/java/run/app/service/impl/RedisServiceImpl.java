package run.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.ClickStatus;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.model.BlogStatus;
import run.app.exception.BadRequestException;
import run.app.exception.UnAuthenticationException;
import run.app.service.RedisService;
import run.app.util.RedisUtil;

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

    //redis通用锁value
    private final String LOCAL_VALUE = "LOCAL_VALUE";

    //    用户点击--redis锁
    private final String LOCAL_BLOG_CLICK_PRE = "LOCAL_BLOG_CLICK_KEY";


    private static final String BLOG_CLICKED_KEY="BLOG_CLICKED";

    private static final String LIST_TOP5_POSTS = "TOP5_POSTS";

//    更新top5文章--redis锁
    private static final String LOCAL_TOP5_POST_UPDATE_KEY = "LOCAL_TOP5_POST_UPDATE_KEY";

//  邮箱验证码--redis锁
    private static final String LOCAL_EMAIL_CODE_RE = "LOCAL_EMAIL_CODE_PRE";

// 用户登陆--redis锁
    private static final String LOCAL_TOKEN_PRE = "LOCAL_TOKEN_PRE";



    @Override
    public void incrementBlogClickedCount(ClickStatus clickStatus) {
        StringBuilder builder = new StringBuilder();
        builder.append(LOCAL_BLOG_CLICK_PRE);
        builder.append(clickStatus.getBlogId());
        Boolean lock = getLock(builder.toString(),LOCAL_VALUE,2,TimeUnit.SECONDS);

        if(!lock){
            log.info("redis正在添加缓存...请稍等"); //此时锁有人占用
            return ;
        }

        try{
            String key = RedisUtil.getClickSetKey(clickStatus);
            if(redisTemplate.opsForSet().isMember(key,clickStatus.getBlogId())){
                return;
            }
            redisTemplate.opsForHash().increment(BLOG_CLICKED_KEY,clickStatus.getBlogId(),clickStatus.getCount());

            redisTemplate.opsForSet().add(key,clickStatus.getBlogId());
            redisTemplate.expire(key,1,TimeUnit.DAYS); //过期时间为1天
//            redisTemplate.expire(key,10,TimeUnit.SECONDS);
        }finally {
            delete(builder.toString());
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
            String key = (String) entry.getKey();
            Long  blogId = Long.valueOf(key);
            Integer count = (Integer) entry.getValue();

            BlogStatus status = new BlogStatus(blogId, count);

            list.add(status);
            redisTemplate.opsForHash().delete(BLOG_CLICKED_KEY,blogId);
        }
        return list;
    }

    @Override
    public void transTop5Posts2Redis(List<PopularBlog> list) {

        Boolean lock = getLock(LOCAL_TOP5_POST_UPDATE_KEY,LOCAL_VALUE,2*60,TimeUnit.SECONDS);
        if(!lock){
            log.info("redis正在添加缓存...请稍等"); //此时锁有人占用
            return ;
        }
        try{
            /**
            * 当文章少于5的时候，为了填满5个会出现一些重复序列
            * @Author: WHOAMI
            * @Date: 2019/12/13 14:10
             */
            //-先增，然后删除，避免在更新的时候有人访问-[不对]
            redisTemplate.delete(LIST_TOP5_POSTS);
            list.stream().filter(Objects::nonNull).forEach(entity ->{
//                PopularBlogRedis blogRedis = new PopularBlogRedis();
//                BeanUtils.copyProperties(entity,blogRedis);
                redisTemplate.opsForZSet().add(LIST_TOP5_POSTS,entity,entity.getClickcount());
            });


//            Long size = redisTemplate.opsForZSet().size(LIST_TOP5_POSTS);
//            if(size>5){
//                Long end = size - 6;
//                redisTemplate.opsForZSet().removeRange(LIST_TOP5_POSTS, 0, end);
//            }

        }finally {
            delete(LOCAL_TOP5_POST_UPDATE_KEY);
        }
    }

    @Override
    public Set<PopularBlog> listTop5FrmRedis() {
        Set set = redisTemplate.opsForZSet().reverseRange(LIST_TOP5_POSTS, 0, 5);

        return set;
    }


    //    获取redis锁
    public Boolean getLock(String key,String value,int timeout,TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout,timeUnit);
    }

    @Override
    public void putAutoToken(String refreshToken, Long userId, int timeout, TimeUnit timeUnit) {

        StringBuilder lockKey = new StringBuilder();

        lockKey.append(LOCAL_TOKEN_PRE);

        lockKey.append(userId);


        Boolean lock = getLock(lockKey.toString(),LOCAL_VALUE,5,TimeUnit.SECONDS);
        if(!lock){
            throw new BadRequestException("请不要频繁登陆");
        }
        try{
            redisTemplate.opsForValue().setIfAbsent(refreshToken,userId,timeout,timeUnit);
        }finally {
            delete(lockKey.toString());
        }

    }

    @Override
    public void putEmailCode(String email, String code, int timeout, TimeUnit timeUnit) {
        StringBuilder lockKey = new StringBuilder();

        lockKey.append(LOCAL_EMAIL_CODE_RE);

        lockKey.append(email);

        Boolean lock = getLock(lockKey.toString(),LOCAL_VALUE,5,TimeUnit.SECONDS);

        if(!lock){
            throw new BadRequestException("请不要频繁操作");
        }
        try{

            redisTemplate.opsForValue().setIfAbsent(code,email,timeout,timeUnit);

        }finally {
            delete(lockKey.toString());
        }
    }

    @Override
    public String getEmailByCode(String code) {
        String string = redisTemplate.opsForValue().get(code).toString();

        delete(code); //删除验证码

        return string;
    }

    @Override
    public Long getUserIdByRefreshToken(String key) {

        Long id =(Long) redisTemplate.opsForValue().get(key);

        if(null == id || id.equals(0L)){
            throw new UnAuthenticationException("ReFreshToken凭证已失效，请重新登录");
        }
        return id;
    }


    //删除redis键值对
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }




}
