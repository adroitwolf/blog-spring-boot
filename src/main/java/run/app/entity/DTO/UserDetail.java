package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/24 10:05
 * Description: :用户详细资料
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetail {
    //这里指的是用户用户名称
    private String nickname;

//    private String phone;
//
//    private String email;

    private String avatar;

    private String aboutMe;

    private List<String> roles;
}
