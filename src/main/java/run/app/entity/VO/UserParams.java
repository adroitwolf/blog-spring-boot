package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/29 7:42
 * Description: 用户详细信息
 */
@Data
@ToString
public class UserParams {
    @NotBlank(message = "昵称不能为空！")
    @Size(max = 20, message = "昵称不能超过{max}")
    private String nickname;

    @Size(max = 50, message = "自我介绍不能超过{max}")
    private String aboutMe;

//    @Email(message = "电子邮件格式不正确")
//    private String email;


}
