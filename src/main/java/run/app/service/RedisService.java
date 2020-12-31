package run.app.service;


import run.app.entity.DTO.ClickStatus;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.model.BlogStatus;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/20 17:32
 * Description: redis服务层
 */
public interface RedisService {

    /**
     * 功能描述: 获取到redis锁
     *
     * @Param: [key, value, timeout, timeUnit]
     * @Return: java.lang.Boolean
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:45
     */
    Boolean getLock(String key, String value, int timeout, TimeUnit timeUnit);


    /**
     * 功能描述: 存放token与access_token机制
     *
     * @Param: [userId, accessToken, refreshToken, timeout, timeUnit]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/2/27 22:03
     */
    void putAutoToken(String accessToken, Long userId, int timeout, TimeUnit timeUnit);

    /**
     * 功能描述: 存放email和code的匹配序列
     *
     * @Param: [email, code, timeout, timeUnit]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/3/10 12:47
     */
    void putEmailCode(String email, String code, int timeout, TimeUnit timeUnit);

    /**
     * 功能描述: 通过验证码获取注册邮箱
     *
     * @Param: [code]
     * @Return: java.lang.String
     * @Author: WHOAMI
     * @Date: 2020/4/6 16:35
     */
    String getEmailByCode(String code);


    /**
     * 功能描述: 通过refreshToken获取用户id，当token无效时会抛出异常
     *
     * @Param: [key]
     * @Return: java.lang.Long
     * @Author: WHOAMI
     * @Date: 2020/4/6 16:38
     */
    Long getUserIdByRefreshToken(String key);

    /**
     * 功能描述: 删除redis键值对
     *
     * @Param: [key]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:45
     */
    void delete(String key);

    /**
     * 功能描述: 增加用户点击量
     *
     * @Param: [clickStatus]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:46
     */
    void incrementBlogClickedCount(ClickStatus clickStatus);

    /**
     * 功能描述: 获取id下的文章的点击量
     *
     * @Param: [blogId]
     * @Return: java.lang.Integer
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:47
     */
    Integer getBlogClickedCount(Long blogId);

    /**
     * 功能描述: 列出缓存中所有博客的点击量 用于更新到数据库中
     *
     * @Param: []
     * @Return: java.util.List<run.app.entity.model.BlogStatus>
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:48
     */
    List<BlogStatus> listBlogClickedCounts();

    /**
     * 功能描述: 在数据库中选出当前的每日精选文章，供之后的用户初始化首页
     *
     * @Param: [list]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:51
     */
    void transTop5Posts2Redis(List<PopularBlog> list);


    /**
     * 功能描述: 在数据库中获取到今日的精选文章
     *
     * @Param: []
     * @Return: java.util.Set<run.app.entity.DTO.PopularBlog>
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:52
     */
    Set<PopularBlog> listTop5FrmRedis();


}
