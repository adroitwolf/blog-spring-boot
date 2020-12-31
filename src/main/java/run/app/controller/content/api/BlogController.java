package run.app.controller.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.service.CommentService;
import run.app.service.PostService;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/2/9 18:17
 * Description: 前台下面的博客控制器
 */
@RestController("ReceBlogController")
@RequestMapping("/api/content/post")
public class BlogController {


}
