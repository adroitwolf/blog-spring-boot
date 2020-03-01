package run.app.service;

import lombok.NonNull;
import run.app.entity.DTO.BaseBlog;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.QueryParams;
import run.app.entity.model.Blog;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:21
 * Description: 文章服务接口
 */
public interface ArticleService {

    /**
    * 功能描述: 发布新文章
    * @Param: [articleParams, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:07
     */
    @NonNull
    BaseResponse submitArticle(@NonNull ArticleParams articleParams,@NonNull String token);

    /**
    * 功能描述: 更新文章，此功能只能文章作者进行操作
    * @Param: [articleParams, blogId, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:07
     */
    BaseResponse updateArticle(@NonNull ArticleParams articleParams,@NonNull Long blogId,@NonNull String token);

    /**
    * 功能描述: 更新文章状态 保证用户和管理员对其操作，然后可以对文章进行审核和丢进垃圾站等操作
    * @Param: [blogId, status, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:09
     */
    BaseResponse updateArticleStatus(@NonNull Long blogId,@NonNull String status,String token);

    /**
    * 功能描述: 由管理员对用户文章状态进行审核
    * @Param: [blogId, status, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:10
     */
    BaseResponse updateArticleStatusByAdmin(@NonNull Long blogId,@NonNull String status,String token);

    /**
    * 功能描述:获取到文章详细内容
    * @Param: [blogId, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:11
     */
    BaseResponse getArticleDetail(@NonNull Long blogId,String token);

    /**
    * 功能描述: 可以获取到用户的所有文章
    * @Param: [pageInfo, postQueryParams, token]
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:11
     */
    BaseResponse getArticleListByExample(PageInfo pageInfo, QueryParams postQueryParams, @NonNull String token);


    /**
    * 功能描述: 管理员获取到所有用户文章
    * @Param: [pageNum, pageSize, postQueryParams, token] 
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:12
     */
    BaseResponse getArticleListToAdminByExample(PageInfo pageInfo, QueryParams postQueryParams, @NonNull String token);


    /**
    * 功能描述: 根据id获取到博客文章名称 -此功能目前用来缓存redis精选文章
    * @Param: [blogId] 
    * @Return: java.lang.String
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:13
     */
    String getArticleNameByBlogId(Long blogId);

    /**
    * 功能描述: 删除博客
    * @Param: [blogId, token] 
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:14
     */
    BaseResponse deleteBlog(@NonNull Long blogId,String token);

    /**
    * 功能描述: 获取当前用户文章数量
    * @Param: [token] 
    * @Return: run.app.entity.DTO.BaseResponse
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:15
     */
    BaseResponse getArticleCount(@NonNull String token);

    /**
    * 功能描述: 根据用户id删除用户所有文章 ,可以用来当作管理员删除用户或者吊销用户
    * @Param: [userId] 
    * @Return: void
    * @Author: WHOAMI
    * @Date: 2020/1/30 18:16
     */
    BaseResponse deletePostsByUserId(Long userId);

    /**
    * 功能描述: 删除引用图片
    * @Param: [picId]
    * @Return: void
    * @Author: WHOAMI
    * @Date: 2020/1/30 19:36
     */
    void deleteQuotePic(Long picId);


    /**
    * 功能描述: 根据博客id来获取博客的基本信息，不包括内容
    * @Param: [id]
    * @Return: run.app.entity.model.Blog
    * @Author: WHOAMI
    * @Date: 2020/1/30 21:11
     */
    Blog getBlogByBlogId(Long id);


    /**
    * 功能描述: 根据id获取博客的最基本信息 其中包括id 标题 图片
    * @Param: [id]
    * @Return: run.app.entity.DTO.BaseBlog
    * @Author: WHOAMI
    * @Date: 2020/2/19 16:42
     */
    BaseBlog getBaseBlogById(Long id);
}
