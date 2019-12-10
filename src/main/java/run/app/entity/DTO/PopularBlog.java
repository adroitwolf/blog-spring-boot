package run.app.entity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 21:41
 * Description: 人气博客
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public final class PopularBlog implements Serializable {
    private String blogName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer clickcount;


}
