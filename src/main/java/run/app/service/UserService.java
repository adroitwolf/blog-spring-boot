package run.app.service;


import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.DTO.UserDTO;
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


    /**
     * 功能描述: 根据用户id获取到该用户的详细资料
     *
     * @Param: [bloggerId]
     * @Return: run.app.entity.DTO.UserDetail
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:11
     */
    @NonNull
    UserDetail findUserDetailByBloggerId(@NonNull Long bloggerId);


    /**
     * 功能描述: 根据角色id更新角色的资料
     *
     * @Param: [userParams, token]
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:12
     */
    @NonNull
    BaseResponse updateProfileById(@NonNull UserParams userParams, @NonNull String token);

    /**
     * 功能描述: 根据token获取到用户的详细资料
     *
     * @Param: [token]
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:13
     */
    BaseResponse getUserDetailByToken(@NonNull String token);


    /**
     * 功能描述: 更新用户的头像
     *
     * @Param: [avatar, token]
     * @Return: run.app.entity.DTO.BaseResponse
     * @Author: WHOAMI
     * @Date: 2020/1/30 20:13
     */
    BaseResponse updateAvatar(@NonNull MultipartFile avatar, @NonNull String token);


//     Integer getUserIdByToken(@NonNull String token);


    /**
     * 功能描述: 获取用户基本信息 这里一般是前台页面的用户信息基本展示
     *
     * @Author: WHOAMI
     * @Date: 2020/1/31 11:20
     */

    UserDTO getUserDTOById(Long id);

    /**
     * 功能描述: 根据用户id获取到用户名称
     * eturn: java.lang.String
     *
     * @Author: WHOAMI
     * @Date: 2020/1/31 13:09
     */
    String getNicknameById(Long id);

}
