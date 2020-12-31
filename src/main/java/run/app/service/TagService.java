package run.app.service;

import run.app.entity.VO.PageInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 21:21
 * Description: 标签服务接口
 */
public interface TagService {

    /**
     * 功能描述: 提交新的博客id和标签之间的联系
     *
     * @Param: [tags, blogId]
     * @Return: java.lang.String
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:56
     */
    String submitArticleWithTagString(List<String> tags, Long blogId);

    /**
     * 功能描述: 更新博客与标签之间的联系，这个操作在用户更新操作的时候使用
     *
     * @Param: [blogId, tagsParams]
     * @Return: java.lang.String
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:59
     */
    String updateTagsWithId(Long blogId, List<String> tagsParams);

    /**
     * 功能描述: 根据博客中的标签数据串‘[xxx,xxx,xxx]’解析出当前可用的标签列表
     *
     * @Param: [ids]
     * @Return: java.util.List<java.lang.String>
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:02
     */
    List<String> selectTagTitleByIdString(String ids);

    /**
     * 功能描述: 对当前的标签数据串进行引用人数的更新操作 true代表增加，false代表减少
     *
     * @Param: [ids, status]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:04
     */
    void dealWithNumByIdString(String ids, boolean status);

    /**
     * 功能描述: 根据标签id删除标签
     *
     * @Param: [blogId]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:06
     */
    void deleteTagsKeyByBlogId(Long blogId);

    /**
     * 功能描述: 根据标签名找出当前id
     *
     * @Param: [tag]
     * @Return: java.lang.Long
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:06
     */
    Long selectIdWithName(String tag);


    /**
     * 功能描述:根据标签id获取到使用该id的文章集合
     *
     * @Param: [pageInfo, id]
     * @Return: java.util.List<java.lang.Long>
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:11
     */
    List<Long> selectBlogIdByTagId(PageInfo pageInfo, Long id);
}
