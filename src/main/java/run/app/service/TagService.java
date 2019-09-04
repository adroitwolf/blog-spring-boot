package run.app.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 21:21
 * Description: ://TODO ${END}
 */
public interface TagService {

    String submitArticleWithTagString(List<String> tags,Integer blogId);

    String updateTagsWithId(Integer blogId, List<String> tagsParams);

    List<String> selectTagTitleByIdString(String ids);

    void dealWithNumByIdString(String ids,boolean status);

    void deleteTagsKeyByBlogId(Integer blogId);

    Integer selectIdWithName(String tag);



    List<Integer> selectBlogIdByTagId(Integer id);
}
