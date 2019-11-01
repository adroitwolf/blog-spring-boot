package run.app.controller.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:41
 * Description: 用户管理控制器
 */

@RestController("UserManageController")
@RequestMapping("/manage/usr")
public class UserController {

    @GetMapping("/hello")
    @ApiOperation("测试专用")
    public String hello(){
        return "heelo";
    }
}
