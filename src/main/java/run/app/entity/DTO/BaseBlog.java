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
 * Time: 2020 2020/2/19 16:32
 * Description: 博客的基础信息
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseBlog {

    private String title;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String picture;

}
