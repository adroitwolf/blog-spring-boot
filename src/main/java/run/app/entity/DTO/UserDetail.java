package run.app.entity.DTO;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/24 10:05
 * Description: :用户详细资料
 */
@Data
public class UserDetail {
    private String username;

    private String phone;

    private String email;

    private String avatarId;

    private String aboutMe;

    private List<String> roles;
}
