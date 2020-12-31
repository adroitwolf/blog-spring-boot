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
 * Time: 2019 2019/9/3 23:05
 * Description: :传输给用户的图片bean
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Picture {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String path;

    private String title;


}
