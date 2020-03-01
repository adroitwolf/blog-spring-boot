package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.ArticleParams;
import run.app.aop.annotation.MethodLog;
import run.app.entity.VO.QueryParams;
import run.app.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/25 21:25
 * Description: :博客相关控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final static String AUTHENICATION = "Authentication";

    @Autowired
    ArticleService articleService;

    @MethodLog
    @ApiOperation("提交新的博客文档")
    @PostMapping("/submit")
    public BaseResponse submitArticle(@Valid @RequestBody ArticleParams articleParams, HttpServletRequest request){
        log.info(articleParams.toString());
        return articleService.submitArticle(articleParams, request.getHeader(AUTHENICATION));
    }

    @MethodLog
    @ApiOperation("更新博客状态的操作")
    @PutMapping("/{BlogId:\\d+}/status/{status}")
    public BaseResponse updateArticleStatus(@PathVariable("BlogId")Long blogId ,
                              @PathVariable("status")String status,HttpServletRequest request){
        return articleService.updateArticleStatus(blogId,status,request.getHeader(AUTHENICATION));
    }


    @ApiOperation("查看博客详细内容")
    @GetMapping("detail/{BlogId:\\d+}")
    public BaseResponse getBlogDetail(@PathVariable("BlogId")Long blogId,HttpServletRequest request){
        return  articleService.getArticleDetail(blogId,request.getHeader(AUTHENICATION));
    }

    @MethodLog
    @ApiOperation("更新博客文档")
    @PutMapping("{BlogId:\\d+}")
    public BaseResponse updateArticle(@PathVariable("BlogId")Long blogId ,@Valid @RequestBody ArticleParams articleParams,
                                      HttpServletRequest request){

        log.debug(articleParams.toString());

        return articleService.updateArticle(articleParams,blogId,request.getHeader(AUTHENICATION));
    }


    @MethodLog
    @ApiOperation("文章查询")
    @GetMapping("query")
    public BaseResponse getListByExample(PageInfo pageInfo,
                                         QueryParams postQueryParams,
                                         HttpServletRequest request){

        log.info(postQueryParams.toString());
        return articleService.getArticleListByExample(pageInfo,postQueryParams,request.getHeader(AUTHENICATION));
    }

    @MethodLog
    @ApiOperation("删除博客")
    @DeleteMapping("{blogId:\\d+}")
    public BaseResponse deleteBlog(@PathVariable("blogId")Long blogId,HttpServletRequest request){

        return articleService.deleteBlog(blogId,request.getHeader(AUTHENICATION));
    }

    @ApiOperation("获取博客数量")
    @GetMapping("count")
    public BaseResponse countList(HttpServletRequest request){
        return articleService.getArticleCount( request.getHeader(AUTHENICATION));
    }


}
