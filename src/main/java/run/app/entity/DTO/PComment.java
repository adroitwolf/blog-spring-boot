package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/31 15:52
 * Description: 父类评论
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PComment extends Comment{

    private long children_count;


    private List<Comment> children;
}
