package run.app.service;

import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:45
 * Description: ://针对前台博客的服务
 */
public interface PostService {
    BaseResponse getList(int pageNum,int pageSize);

    BaseResponse getListByExample(int pageNum, int pageSize, String keyword);

    BlogDetailWithAuthor getDetail(Long blogId);

    BaseResponse getListByTag(int pageNum,int pageSize,String tag);

    BaseResponse getTopPosts();
}
