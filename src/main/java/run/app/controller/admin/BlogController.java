package run.app.controller.admin;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.QueryParams;
import run.app.service.ArticleService;
import run.app.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/15 14:41
 * Description: 博客管理控制器
 */
@RestController("PostManagerController")
@RequestMapping("/manage/post")
@Slf4j
public class BlogController {
    @Autowired
    ArticleService articleService;

    @Autowired
    PostService postService;

    private final static String AUTHENICATION = "Authentication";

    @GetMapping("/query")
    @ApiOperation("管理员审核文章列表")
    public BaseResponse getListByExample(@Valid PageInfo pageInfo,
                                         QueryParams postQueryParams,
                                         HttpServletRequest request){

        log.info(postQueryParams.toString());
        return articleService.getArticleListToAdminByExample(pageInfo,postQueryParams,request.getHeader(AUTHENICATION));
    }

    @GetMapping("/detail/{blogId:\\d+}")
    @ApiOperation("博客详细信息")
    public BaseResponse getDetail(@PathVariable("blogId")Long blogId){
        return postService.getArticleDetail(blogId);
    }


    @PutMapping("/check/{blogId:\\d+}/result/{result}")
    @ApiOperation("文章审核状态")
    public BaseResponse checkUserArticle(@PathVariable("result")String result,@PathVariable("blogId")Long blogId,HttpServletRequest request){

        return articleService.updateArticleStatusByAdmin(blogId,result,request.getHeader(AUTHENICATION));
    }

}
