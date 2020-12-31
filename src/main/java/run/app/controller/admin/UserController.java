package run.app.controller.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.entity.VO.QueryParams;
import run.app.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:41
 * Description: 用户管理控制器
 */

@RestController("UserManageController")
@RequestMapping("/manage/usr")
public class UserController {

    @Autowired
    AccountService accountService;

    private final static String TOKEN = "Authentication";

    @PutMapping("/userUpdate/{bloggerId:\\d+}/{status}")
    @ApiOperation("管理帐户状态")
    public BaseResponse updateUserStatus(@PathVariable("bloggerId") Long bloggerId,
                                         @PathVariable("status") String status, HttpServletRequest request) {
        return accountService.updateUserStatus(bloggerId, status, request.getHeader(TOKEN));
    }

    @DeleteMapping("/delete/{bloggerId:\\d+}")
    @ApiOperation("删除用户")
    public BaseResponse deleteUser(@PathVariable("bloggerId") Long bloggerId, HttpServletRequest request) {
        return accountService.deleteUser(bloggerId, request.getHeader(TOKEN));
    }


    @GetMapping("/query")
    @ApiOperation("查询用户状态")
    public BaseResponse queryUserByExample(
            @Valid PageInfo pageInfo,
            QueryParams queryParams) {
        return accountService.selectUserByExample(pageInfo, queryParams);
    }


}
