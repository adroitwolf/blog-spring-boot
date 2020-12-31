package run.app.service;


import run.app.entity.DTO.Blog;
import run.app.entity.DTO.PopularBlog;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 13:40
 * Description:博客状态服务
 */
public interface BlogStatusService {

    /**
     * 功能描述: 讲当前缓存所有用户的点击量存到数据库中
     *
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:40
     */
    void transClickedCountFromRedis2DB();

    /**
     * 功能描述: 在数据库中获取到精选文章
     *
     * @Return: java.util.List<run.app.entity.DTO.PopularBlog>
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:43
     */
    List<PopularBlog> listTop5Posts();
}
