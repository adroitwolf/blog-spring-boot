package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.params.LoginParams;
import run.app.entity.params.PasswordParams;
import run.app.security.log.MethodLog;
import run.app.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:00
 * Description: ://TODO ${END}
 */
@RestController
@RequestMapping("api/admin")

public class AdminController {

    @Autowired
    AccountService accountService;


    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public String  login(@RequestBody @Valid LoginParams loginParams){
        return accountService.loginService(loginParams).get();
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
