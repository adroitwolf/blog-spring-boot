package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/2/19 16:28
 * Description: 后台管理的评论模型
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MComment {
    Comment self;
    Comment parent;

    BaseBlog blog;
}
