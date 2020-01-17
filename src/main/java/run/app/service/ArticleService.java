package run.app.service;

import lombok.NonNull;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.QueryParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:21
 * Description: 文章服务接口
 */
public interface ArticleService {

    @NonNull
    BaseResponse submitArticle(@NonNull ArticleParams articleParams,@NonNull String token);

    BaseResponse updateArticle(@NonNull ArticleParams articleParams,@NonNull Long blogId,@NonNull String token);

    BaseResponse updateArticleStatus(@NonNull Long blogId,@NonNull String status,String token);

    BaseResponse updateArticleStatusByAdmin(@NonNull Long blogId,@NonNull String status,String token);

    BaseResponse getArticleDetail(@NonNull Long blogId,String token);

    //用户
    BaseResponse getArticleListByExample(@NonNull int pageNum, @NonNull int pageSize, QueryParams postQueryParams, @NonNull String token);


    //管理员
    BaseResponse getArticleListToAdminByExample(@NonNull int pageNum, @NonNull int pageSize, QueryParams postQueryParams, @NonNull String token);
    String getArticleNameByBlogId(Long blogId);

    void deleteBlog(@NonNull Long blogId,String token);

    BaseResponse getArticleCount(@NonNull String token);

    void deletePostsByUserId(Long userId);

    void deleteQuotePic(Long picId);
}
