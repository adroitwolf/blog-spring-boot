package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.UserDetail;
import run.app.entity.params.LoginParams;
import run.app.entity.params.PasswordParams;
import run.app.entity.params.UserParams;
import run.app.security.log.MethodLog;
import run.app.security.token.AuthToken;
import run.app.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 7:33
 * Description: ://TODO ${END}
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public AuthToken login(@RequestBody @Valid LoginParams loginParams){
        return userService.loginService(loginParams);
    }


    @MethodLog
    @ApiOperation("获取用户详细信息")
    @GetMapping("/getUserDetail")
    public UserDetail getUserDetail(HttpServletRequest request){

        return userService.getUserDetailByToken(request.getHeader("Authentication"));
    }

    @MethodLog
    @ApiOperation("更改用户密码")
    @PutMapping("/changePassword")
    public BaseResponse updatePassword(@Valid @RequestBody PasswordParams passwordParams,HttpServletRequest request){
        String token = userService.getUsernameByToken(request.getHeader("Authentication"));
        BaseResponse baseResponse = new BaseResponse();
        if(userService.updatePassword(passwordParams.getOldPassword(),passwordParams.getNewPassword(),token)){
            baseResponse.setStatus(HttpStatus.OK.value());
        }else{
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return baseResponse;
    }

    @MethodLog
    @ApiOperation("更新用户资料")
    @PutMapping("/profile")
    public  BaseResponse updateProfile( @RequestBody UserParams userParams,HttpServletRequest request){
        log.debug(userParams.toString());
        String token = request.getHeader("Authentication");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userService.updateProfileById(userParams,token));
        baseResponse.setStatus(HttpStatus.OK.value());

        return baseResponse;
    }

    @MethodLog
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public BaseResponse logout(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();

        String token = request.getHeader("Authentication");

        if(userService.logout(token)){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }




}
