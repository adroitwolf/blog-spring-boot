package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.PasswordParams;
import run.app.entity.VO.RegisterParams;
import run.app.security.annotation.MethodLog;
import run.app.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

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
    public BaseResponse  login(@RequestBody @Valid LoginParams loginParams){

        return accountService.loginService(loginParams);
    }



    @MethodLog
    @ApiOperation("更改用户密码")
    @PutMapping("/changePassword")
    public BaseResponse updatePassword(@Valid @RequestBody PasswordParams passwordParams, HttpServletRequest request){
//        String token = userService.getUsernameByToken(request.getHeader("Authentication"));
            String token = request.getHeader("Authentication");
        BaseResponse baseResponse = new BaseResponse();
        if(accountService.updatePassword(passwordParams.getOldPassword(),passwordParams.getNewPassword(),token)){
            baseResponse.setStatus(HttpStatus.OK.value());
        }else{
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return baseResponse;
    }

    @ApiOperation("注册新用户")
    @PostMapping("/register")
    public BaseResponse register(@Valid @RequestBody RegisterParams registerParams){
        return accountService.registerUser(registerParams);
    }


    //    @MethodLog
//    @PostMapping("/logout")
//    @ApiOperation("用户登出")
//    public BaseResponse logout(HttpServletRequest request){
//        BaseResponse baseResponse = new BaseResponse();
//
//        String token = request.getHeader("Authentication");
//
//        if(userService.logout(token)){
//            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        baseResponse.setStatus(HttpStatus.OK.value());
//        return baseResponse;
//    }


}
