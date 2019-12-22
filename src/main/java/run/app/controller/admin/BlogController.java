package run.app.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import run.app.service.ArticleService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/15 14:41
 * Description: 博客管理控制器
 */
@RestController("/manage/post")
public class BlogController {
    @Autowired
    ArticleService articleService;




}
