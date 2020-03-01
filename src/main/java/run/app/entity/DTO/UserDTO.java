package run.app.entity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/31 10:52
 * Description: 用于用户对外显示基本信息
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class UserDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //这里指的是用名称
    private String nickname;

    private String avatar;

}
