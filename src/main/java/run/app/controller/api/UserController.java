package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.ImageFile;
import run.app.entity.DTO.UserDetail;
import run.app.entity.VO.UserParams;
import run.app.exception.BadRequestException;
import run.app.security.annotation.MethodLog;
import run.app.service.UserService;
import run.app.util.UploadUtil;

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


    @MethodLog
    @ApiOperation("获取用户详细信息")
    @GetMapping("/getUserDetail")
    public BaseResponse getUserDetail(HttpServletRequest request){

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


    @ApiOperation("上传用户图片")
    @PostMapping("/updateAvatar")
    public BaseResponse updateProfile(@RequestParam(value = "avatar",required = true)MultipartFile avatar,
                                      HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();

        UploadUtil instance = UploadUtil.getInstance();

        ImageFile imageFile = instance.uploadFile(avatar).orElseThrow(()->new BadRequestException("用户更新头像失败"));

        String token = request.getHeader("Authentication");

        //todo:保存到数据库
        userService.uploadAvatarId(imageFile.getPath(),token);
        baseResponse.setData(imageFile.getPath());
        baseResponse.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }



}
