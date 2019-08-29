package run.app.service;

import run.app.entity.DTO.BlogDetailWithAuthor;
import run.app.entity.DTO.DataGrid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:45
 * Description: ://针对前台博客的服务
 */
public interface PostService {
    DataGrid getList(int pageNum,int pageSize);

    DataGrid getListByExample(int pageNum,int pageSize,String keyword);

    BlogDetailWithAuthor getDetail(Integer blogId);

    DataGrid getListByTag(int pageNum,int pageSize,String tag);

}
