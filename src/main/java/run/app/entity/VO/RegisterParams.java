package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/17 8:56
 * Description: 注册变量
 */
@Data
@ToString
public class RegisterParams {

    @NotBlank(message = "用户名不能为空！")
    @Size(max=255,message = "用户名称超出长度{max}")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(max=100,message = "密码长度不能超过{max}")
    private String password;


    @NotBlank(message = "手机号不能为空！")
    @Size(max = 11,message = "手机号位数不正确")
    private String phone;

    @Email(message = "电子邮件格式不正确")
    @NotBlank(message = "邮箱不能为空！")
    @Size(max = 127,message = "邮箱长度不能超过{max}")
    private String email;


    @Size(max=10,message = "邀请码格式不正确")
    private String Code;



}
