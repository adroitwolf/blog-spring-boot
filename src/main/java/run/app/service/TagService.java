package run.app.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 21:21
 * Description: ://TODO ${END}
 */
public interface TagService {


    String updateTagsWithId(Integer blogId, List<String> tagsParams);

    List<String> selectTagTitleByIdString(String ids);

    void dealWithNumByIdString(String ids,boolean status);

    void deleteTagsKeyByBlogId(Integer blogId);
}
