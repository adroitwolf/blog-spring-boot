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
    void transClickedCountFromRedis2DB();

    List<PopularBlog> listTop5Posts();
}
