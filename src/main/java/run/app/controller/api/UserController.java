package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.UserParams;
import run.app.aop.annotation.MethodLog;
import run.app.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 7:33
 * Description: :用户控制层
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserService userService;

    private final static String AUTHENICATION = "Authentication";

    @MethodLog
    @ApiOperation("获取用户详细信息")
    @GetMapping("/getUserDetail")
    public BaseResponse getUserDetail(HttpServletRequest request){

        return userService.getUserDetailByToken(request.getHeader(AUTHENICATION));
    }


    @MethodLog
    @ApiOperation("更新用户资料")
    @PutMapping("/profile")
    public  BaseResponse updateProfile( @RequestBody UserParams userParams,HttpServletRequest request){
        log.debug(userParams.toString());
        String token = request.getHeader(AUTHENICATION);
        return userService.updateProfileById(userParams,token);
    }


    @ApiOperation("上传用户图片")
    @PostMapping("/updateAvatar")
    public BaseResponse updateProfile(@RequestParam(value = "avatar",required = true)MultipartFile avatar,
                                      HttpServletRequest request){
      return userService.updateAvatar(avatar,request.getHeader(AUTHENICATION));
    }



}
