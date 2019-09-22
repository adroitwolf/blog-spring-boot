package run.app.entity.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/25 21:17
 * Description: :用户可以搜索的条件
 */
@Data
@ToString
@NoArgsConstructor
public class PostQueryParams {
    private String keyword;

    private String status;

}
