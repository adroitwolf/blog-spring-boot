package run.app.controller.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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


    @ApiOperation("上传用户图片")
    @PostMapping("/updateAvatar")
    public BaseResponse updateProfile(@RequestParam(value = "avatar",required = true)MultipartFile avatar,
                                      HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse();

        String property = System.getProperty("user.dir");


        String filePath = property + File.separator + "avatar";


        String trueFilename = UUID.randomUUID().toString();


        String filename = avatar.getOriginalFilename();

        String type = filename.indexOf(".") != -1 ? filename.substring(filename.lastIndexOf(".")+1,filename.length()):null;

        String trueFile = null == type ? filePath + File.separator + trueFilename :
                filePath + File.separator + trueFilename + "." + type;

        try {
            avatar.transferTo(new File(trueFile));
        } catch (IOException e) {
            e.printStackTrace();
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("图片上传失败");

            return baseResponse;
        }

        String token = request.getHeader("Authentication");

//保存到数据库
        userService.uploadAvatarId(trueFilename+"." +type,token);

        baseResponse.setData(trueFilename+"." +type);

        return baseResponse;
    }



}
