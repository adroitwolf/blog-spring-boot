package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 7:36
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class LoginParams {

    @NotBlank(message = "用户名不能为空！")
    @Size(max=255,message = "用户名称超出长度{max}")
    private String p;

    @NotBlank(message = "密码不能为空")
    @Size(max=100,message = "密码长度不能超过{max}")
    private String password;
}
