package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/25 14:00
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class PasswordParams {
    @NotBlank(message = "密码不能为空")
    @Size(max = 100,message = "超出特定密码长度")
    private String oldPassword;



    @NotBlank(message = "密码不能为空")
    @Size(max = 100,message = "超出特定密码长度")
    private String newPassword;

}
