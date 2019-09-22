package run.app.service;

import lombok.NonNull;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.LoginParams;
import run.app.entity.VO.RegisterParams;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/16 9:00
 * Description: ://TODO ${END}
 */
public interface AccountService {

    //     登陆服务
    @NonNull
    Optional<String> loginService(@NonNull LoginParams loginParams);


    boolean updatePassword(@NonNull String oldPassword,@NonNull String newPassword,String token);


    String getUsernameByToken(@NonNull String token);

    @NonNull
    Integer findBloggerIdByUsername(@NonNull String username);


    BaseResponse registerUser(@NonNull RegisterParams registerParams);

}
