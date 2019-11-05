package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import run.app.entity.enums.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 16:28
 * Description: 用户账号
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;

    private List<String> roles;



}
