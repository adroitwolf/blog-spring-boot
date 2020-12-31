package run.app.service;

import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:45
 * Description: ://针对前台博客的服务
 */
public interface PostService {

    /**
     * 功能描述: 获取文章列表
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 17:48
     */
    BaseResponse getArticleList(PageInfo pageInfo);

    /**
     * 功能描述: 根据条件搜索文章
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 17:48
     */
    BaseResponse getArticleListByExample(PageInfo pageInfo, String keyword);

    /**
     * 功能描述: 获取文章详细内容
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 17:48
     */
    BaseResponse getArticleDetail(Long blogId);

    /**
     * 功能描述: 根据tag标签获取到文章列表
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 17:49
     */
    BaseResponse getArticleListByTag(PageInfo pageInfo, String tag);

    /**
     * 功能描述: 获取到每日精选文章
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 17:51
     */
    BaseResponse getTopPosts();
}
