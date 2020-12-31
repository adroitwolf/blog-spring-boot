package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/22 20:47
 * Description: 用户详细信息
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends User {

    private String email;

    private String aboutMe;

    private String nickname;

    private Long avatarId;

    private String avatar;

    private String registerDate;

    private String isEnabled;

}
