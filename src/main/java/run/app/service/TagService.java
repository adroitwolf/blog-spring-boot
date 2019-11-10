package run.app.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 21:21
 * Description: 标签服务接口
 */
public interface TagService {

    String submitArticleWithTagString(List<String> tags,Long blogId);

    String updateTagsWithId(Long blogId, List<String> tagsParams);

    List<String> selectTagTitleByIdString(String ids);

    void dealWithNumByIdString(String ids,boolean status);

    void deleteTagsKeyByBlogId(Long blogId);

    Long selectIdWithName(String tag);



    List<Long> selectBlogIdByTagId(int pageSize,int pageNum,Long id);
}
