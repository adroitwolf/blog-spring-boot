package run.app.entity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:48
 * Description: 前台博客返回带有作者的文章类
 */
@Data
@ToString
@NoArgsConstructor
public class BlogDetailWithAuthor extends BlogDetail {

    private UserDTO author;

}
