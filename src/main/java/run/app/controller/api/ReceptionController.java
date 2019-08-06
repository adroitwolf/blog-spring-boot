package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.app.entity.DTO.DataGrid;
import run.app.service.ArticleService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/6 11:02
 * Description: ://TODO ${END}
 */
@RestController
@RequestMapping("/post")
public class ReceptionController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/list")
    @ApiOperation("获取当前所有文章，并且默认按照创建时间排序")
    public DataGrid getList(@RequestParam int pageNum,
                            @RequestParam int pageSize){
        return articleService.getArticleList(pageNum,pageSize);
    }
}
