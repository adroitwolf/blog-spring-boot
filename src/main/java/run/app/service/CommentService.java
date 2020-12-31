package run.app.service;

import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.CommentParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/26 10:30
 * Description: 评论服务
 */
public interface CommentService {

    /**
     * 功能描述: 用户发布评论
     *
     * @Author: WHOAMI
     * @Date: 2020/1/28 16:24
     */
    BaseResponse comment(CommentParams commentParams, String token);


    /**
     * 功能描述: 获取id对象下的所有评论
     *
     * @Param: [pageInfo, token]
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 21:01
     */
    BaseResponse getList(Long id, String type, PageInfo pageInfo);


    /**
     * 功能描述: 获得自己所有的文章评论 用来评论管理
     *
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/2/8 11:26
     */
    BaseResponse getListByToken(PageInfo pageInfo, String token);


    /**
     * 功能描述: 根据评论删除id
     *
     * @Param: [commentId, token]
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/2/15 12:52
     */
    BaseResponse deleteComment(Long commentId, String token);
}
