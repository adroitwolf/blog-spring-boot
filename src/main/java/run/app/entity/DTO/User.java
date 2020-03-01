package run.app.entity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //这里指的是用户账号
    private String username;

    private List<String> roles;


}
