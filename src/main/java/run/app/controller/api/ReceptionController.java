package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.DataGrid;
import run.app.service.PostService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/6 11:02
 * Description: :博客前台控制器
 */
@RestController
@RequestMapping("/post")
public class ReceptionController {

    @Autowired
    PostService postService;

    @GetMapping("/query")
    @ApiOperation("搜索文章")
    public BaseResponse getListByExample(@RequestParam int pageNum,
                                     @RequestParam int pageSize,
                                     @RequestParam String keyword){
        return postService.getListByExample(pageNum,pageSize,keyword);
    }

    @GetMapping("tag")
    @ApiOperation("查询相应tag标签")
    public BaseResponse searchTagBlogList(@RequestParam int pageNum,
                                      @RequestParam int pageSize,
                                      @RequestParam String tag){
        return postService.getListByTag(pageNum,pageSize,tag);
    }

    @GetMapping("/list")
    @ApiOperation("获取当前所有文章，并且默认按照创建时间排序")
    public BaseResponse getList(@RequestParam int pageNum,
                            @RequestParam int pageSize){
        return postService.getList(pageNum,pageSize);
    }

    @ApiOperation("获取博客详细内容")
    @GetMapping("/detail/{BlogId:\\d+}")
    public BaseResponse getBlogDetail(@PathVariable("BlogId")Long blogId){

        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setData(postService.getDetail(blogId));
        return baseResponse;
    }
}
