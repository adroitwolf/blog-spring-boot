package run.app.service;

import lombok.NonNull;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.PostQueryParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:21
 * Description: ://TODO ${END}
 */
public interface ArticleService {

    @NonNull
    boolean submitArticle(@NonNull ArticleParams articleParams,@NonNull String token);

    boolean updateArticle(@NonNull ArticleParams articleParams,@NonNull Long blogId,@NonNull String token);

    boolean updateArticleStatus(@NonNull Long blogId,@NonNull String status,String token);


    BlogDetail getArticleDetail(@NonNull Long blogId,String token);


    DataGrid getArticleListByExample(@NonNull int pageNum, @NonNull int pageSize, PostQueryParams postQueryParams, @NonNull String token);


    void deleteBlog(@NonNull Long blogId,String token);

    long getArticleCount(@NonNull String token);


}
