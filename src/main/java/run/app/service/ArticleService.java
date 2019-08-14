package run.app.service;

import io.swagger.models.auth.In;
import lombok.NonNull;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.params.ArticleParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 7:21
 * Description: ://TODO ${END}
 */
public interface ArticleService {

    @NonNull
    boolean submitArticle(@NonNull ArticleParams articleParams,@NonNull String token);

    boolean updateArticle(@NonNull ArticleParams articleParams,@NonNull Integer blogId,@NonNull String token);

    boolean updateArticleStatus(@NonNull Integer blogId,@NonNull String status);

    BlogDetail getArticleDetail(@NonNull Integer blogId);

    @NonNull
    DataGrid getArticleList(@NonNull int pageNum,@NonNull int pageSize,@NonNull String token);



    @NonNull
    DataGrid getArticleList(@NonNull int pageNum,@NonNull int pageSize);

    void deleteBlog(@NonNull Integer blogId);

    long getArticleCount(@NonNull String token);


}
