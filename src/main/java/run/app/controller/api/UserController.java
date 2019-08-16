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




    @MethodLog
    @ApiOperation("获取用户详细信息")
    @GetMapping("/getUserDetail")
    public UserDetail getUserDetail(HttpServletRequest request){

        return userService.getUserDetailByToken(request.getHeader("Authentication"));
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





}
