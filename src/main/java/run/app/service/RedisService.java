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

    Boolean getLock(String key, String value, int timeout, TimeUnit timeUnit);

    void deleteLock(String key);

    void incrementBlogClickedCount(ClickStatus clickStatus);

    Integer getBlogClickedCount(Long blogId);

    List<BlogStatus> listBlogClickedCounts();

    void transTop5Posts2Redis(List<PopularBlog> list);

    Set<PopularBlog> listTop5FrmRedis();


}
