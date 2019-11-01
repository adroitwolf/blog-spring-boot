package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.BlogDetail;
import run.app.entity.DTO.DataGrid;
import run.app.entity.VO.ArticleParams;
import run.app.entity.VO.PostQueryParams;
import run.app.security.annotation.MethodLog;
import run.app.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
        String token = request.getHeader(AUTHENICATION);

        articleService.submitArticle(articleParams,token);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("上传成功");
        return baseResponse;
    }

    @MethodLog
    @ApiOperation("博客-回收站之间的操作")
    @PutMapping("/{BlogId:\\d+}/status/{status}")
    public BaseResponse updateArticleStatus(@PathVariable("BlogId")Long blogId ,
                              @PathVariable("status")String status,HttpServletRequest request){
        articleService.updateArticleStatus(blogId,status,request.getHeader(AUTHENICATION));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        Map<String,String> updateStatus = new HashMap<>();
        updateStatus.put("status",status);
        baseResponse.setData(updateStatus);
        return baseResponse;
    }


    @ApiOperation("查看博客详细内容")
    @GetMapping("detail/{BlogId:\\d+}")
    public BaseResponse getBlogDetail(@PathVariable("BlogId")Long blogId,HttpServletRequest request){

        BlogDetail articleDetail = articleService.getArticleDetail(blogId,request.getHeader(AUTHENICATION));
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(articleDetail);
        return  baseResponse;
    }

    @MethodLog
    @ApiOperation("更新博客文档")
    @PutMapping("{BlogId:\\d+}")
    public BaseResponse updateArticle(@PathVariable("BlogId")Long blogId ,@Valid @RequestBody ArticleParams articleParams,
                                      HttpServletRequest request){

        log.debug(articleParams.toString());

        articleService.updateArticle(articleParams,blogId,request.getHeader(AUTHENICATION));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }


    @MethodLog
    @ApiOperation("文章查询")
    @GetMapping("query")
    public BaseResponse getListByExample(@RequestParam int pageNum,
                                     @RequestParam int pageSize,
                                     PostQueryParams postQueryParams,
                                     HttpServletRequest request){

        log.info(postQueryParams.toString());
        return articleService.getArticleListByExample(pageNum,pageSize,postQueryParams,request.getHeader(AUTHENICATION));
    }

    @MethodLog
    @ApiOperation("删除博客")
    @DeleteMapping("{blogId:\\d+}")
    public BaseResponse deleteBlog(@PathVariable("blogId")Long blogId,HttpServletRequest request){
        articleService.deleteBlog(blogId,request.getHeader(AUTHENICATION));
        return new BaseResponse(HttpStatus.OK.value(),"删除成功",null);
    }

    @ApiOperation("获取博客数量")
    @GetMapping("count")
    public BaseResponse countList(HttpServletRequest request){

        long articleCount = articleService.getArticleCount( request.getHeader(AUTHENICATION));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.OK.value());

        baseResponse.setData(articleCount);
        return baseResponse;
    }


}
