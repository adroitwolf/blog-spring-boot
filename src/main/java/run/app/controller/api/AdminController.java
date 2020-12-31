package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.AutoToken;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.PasswordParams;
import run.app.entity.VO.RegisterParams;
import run.app.aop.annotation.MethodLog;
import run.app.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:00
 * Description: :账户安全相关控制层
 */
@RestController
@RequestMapping("api/admin")

public class AdminController {

    @Autowired
    AccountService accountService;


    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody @Valid LoginParams loginParams) {

        return accountService.loginService(loginParams);
    }

    @MethodLog
    @ApiOperation("更改用户密码")
    @PutMapping("/changePassword")
    public BaseResponse updatePassword(@Valid @RequestBody PasswordParams passwordParams, HttpServletRequest request) {
//        String token = userService.getUsernameByToken(request.getHeader("Authentication"));

        return accountService.updatePassword(passwordParams.getOldPassword(), passwordParams.getNewPassword(), request.getHeader("Authentication"));
    }

    @ApiOperation("发送邮箱验证码")
    @GetMapping("/getMailCode/{mail}")
    public void getMailCode(@PathVariable("mail") String mail) {
        accountService.getMailCode(mail);
    }

    @ApiOperation("注册新用户")
    @PostMapping("/register")
    public BaseResponse register(@Valid @RequestBody RegisterParams registerParams) {
        return accountService.registerUser(registerParams);
    }


    @ApiOperation("获取refresh令牌")
    @GetMapping("/refresh/{refreshToken}")
    public BaseResponse refresh(@PathVariable("refreshToken") String refreshToken) {
        return accountService.refresh(refreshToken);
    }


    @MethodLog
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public BaseResponse logout(AutoToken autoToken) {
        return accountService.logout(autoToken);
    }


}
