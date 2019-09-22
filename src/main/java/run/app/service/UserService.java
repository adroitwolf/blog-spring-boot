package run.app.service;


import lombok.NonNull;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.BloggerProfileWithBLOBs;
import run.app.entity.VO.UserParams;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/6/26 21:33
 * Description: :用户服务层接口
 */

public interface UserService {



     @NonNull
     BloggerProfileWithBLOBs findUserDetailByBloggerId(@NonNull Integer bloggerId);


     @NonNull UserDetail updateProfileById(@NonNull UserParams userParams,@NonNull String token);

     UserDetail getUserDetailByToken(@NonNull String token);


     void uploadAvatarId(@NonNull String avatar,@NonNull String token);




//     Integer getUserIdByToken(@NonNull String token);

     boolean logout(String token);

}
