package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.service.CommentService;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/25 20:13
 * Description: 评论控制器
 */

@RestController
@Slf4j
@RequestMapping("/api/comments")
public class CommentController {


    @Autowired
    CommentService commentService;

    private final static String TOKEN = "Authentication";


    @ApiOperation("管理评论列表")
    @GetMapping("/list")
    public BaseResponse getList(PageInfo pageInfo, HttpServletRequest request){
        return commentService.getListByToken(pageInfo,request.getHeader(TOKEN));
    }


    @DeleteMapping("{commentId:\\d+}/del")
    @ApiOperation("删除评论")
    public BaseResponse deleteComment(@PathVariable("commentId") Long commentId,HttpServletRequest request){
        return commentService.deleteComment(commentId,request.getHeader(TOKEN));
    }


}
