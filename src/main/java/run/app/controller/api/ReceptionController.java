package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.aop.annotation.IncrementClickCount;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.CommentParams;
import run.app.service.CommentService;
import run.app.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/6 11:02
 * Description: :博客前台控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/post")
public class ReceptionController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;


    private final static String TOKEN = "Authentication";

    @GetMapping("/query")
    @ApiOperation("搜索文章")
    public BaseResponse getListByExample(@Valid PageInfo pageInfo,
                                         @RequestParam String keyword) {
        return postService.getArticleListByExample(pageInfo, keyword);
    }

    @GetMapping("tag")
    @ApiOperation("查询相应tag标签")
    public BaseResponse searchTagBlogList(@Valid PageInfo pageInfo,
                                          @RequestParam String tag) {
        return postService.getArticleListByTag(pageInfo, tag);
    }

    @GetMapping("/list")
    @ApiOperation("获取当前所有文章，并且默认按照创建时间排序")
    public BaseResponse getList(PageInfo pageInfo) {
        return postService.getArticleList(pageInfo);
    }

    @ApiOperation("获取博客详细内容")
    @GetMapping("/detail/{BlogId:\\d+}")
    @IncrementClickCount
    public BaseResponse getBlogDetail(@PathVariable("BlogId") Long blogId) {
        return postService.getArticleDetail(blogId);
    }


    @ApiOperation("获取点击量前五的文章")
    @GetMapping("/top")
    public BaseResponse getTopPosts() {
        return postService.getTopPosts();
    }


    @PostMapping("/comments")
    @ApiOperation("发布评论")
    public BaseResponse comment(@RequestBody @Valid CommentParams commentParams, HttpServletRequest httpServletRequest) {
        log.info(commentParams.toString());
        return commentService.comment(commentParams, httpServletRequest.getHeader(TOKEN));
    }


    @GetMapping("/{id:\\d+}/comments")
    @ApiOperation("获取评论")
    public BaseResponse getList(@PathVariable("id") Long id,
                                @RequestParam String type,
                                PageInfo pageInfo) {
        log.info(pageInfo.toString());
        return commentService.getList(id, type, pageInfo);

    }

}
