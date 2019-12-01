package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public final class PopularBlog {
    String blogName;

    private Long id;

    private Integer clickcount;
}
