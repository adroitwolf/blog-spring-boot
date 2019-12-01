package run.app.service;


import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.BloggerProfile;
import run.app.entity.VO.UserParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/26 21:33
 * Description: :用户服务层接口
 */

public interface UserService {


     @NonNull
     UserDetail findUserDetailByBloggerId(@NonNull Long bloggerId);


     @NonNull UserDetail updateProfileById(@NonNull UserParams userParams,@NonNull String token);

     BaseResponse getUserDetailByToken(@NonNull String token);


     BaseResponse updateAvatar(@NonNull MultipartFile avatar, @NonNull String token);


//     Integer getUserIdByToken(@NonNull String token);

     boolean logout(String token);

}
