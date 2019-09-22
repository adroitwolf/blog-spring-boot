package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/29 7:42
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class UserParams {
    @NotBlank(message = "用户名不能为空！")
    @Size(max = 20,message = "用户名不能超过{max}")
    private String username;

    @NotBlank(message = "手机号不能为空！")
    @Size(max = 11,message = "手机号位数不正确")
    private String phone;

    @NotBlank(message = "邮箱不能为空！")
    @Size(max = 20,message = "邮箱长度不能超过{max}")
    private String email;


    @NotBlank(message = "自我介绍不能为空！")
    @Size(max = 50,message = "自我介绍不能超过{max}")
    private String aboutMe;
}
