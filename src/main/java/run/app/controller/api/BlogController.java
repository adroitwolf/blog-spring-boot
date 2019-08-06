package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.entity.params.ArticleParams;
import run.app.security.log.MethodLog;
import run.app.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/25 21:25
 * Description: ://TODO ${END}
 */
@Slf4j
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    ArticleService articleService;

    @MethodLog
    @ApiOperation("提交新的博客文档")
    @PostMapping("/submit")
    public BaseResponse submitArticle(@Valid @RequestBody ArticleParams articleParams, HttpServletRequest request){
        log.info(articleParams.toString());
        String token = request.getHeader("Authentication");

        articleService.submitArticle(articleParams,token);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }

    @MethodLog
    @ApiOperation("博客-回收站之间的操作")
    @PutMapping("/{BlogId:\\d+}/status/{status}")
    public BaseResponse updateArticleStatus(@PathVariable("BlogId")Integer blogId ,
                              @PathVariable("status")String status){
        articleService.updateArticleStatus(blogId,status);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        Map<String,String> updateStatus = new HashMap<>();
        updateStatus.put("status",status);
        baseResponse.setData(updateStatus);
        return baseResponse;
    }

    @MethodLog
    @ApiOperation("更新博客文档")
    @PutMapping("{BlogId:\\d+}")
    public BaseResponse updateArticle(@PathVariable("BlogId")Integer blogId ,@Valid @RequestBody ArticleParams articleParams,
                                      HttpServletRequest request){

        log.debug(articleParams.toString());

        String token = request.getHeader("Authentication");

        articleService.updateArticle(articleParams,blogId,token);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }

    @MethodLog
    @ApiOperation("获取当前文章所有列表")
    @GetMapping("/list")
    public DataGrid getList(@RequestParam int pageNum,
                            @RequestParam int pageSize,
                            HttpServletRequest request){
        String token = request.getHeader("Authentication");
        return articleService.getArticleList(pageNum,pageSize,token);
    }

    @MethodLog
    @ApiOperation("删除博客")
    @DeleteMapping("{blogId:\\d+}")
    public void deleteBlog(@PathVariable("blogId")Integer blogId){
        articleService.deleteBlog(blogId);
    }

    @ApiOperation("获取博客数量")
    @GetMapping("count")
    public BaseResponse countList(HttpServletRequest request){
        String token = request.getHeader("Authentication");

        long articleCount = articleService.getArticleCount(token);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        baseResponse.setData(articleCount);
        return baseResponse;
    }


}
