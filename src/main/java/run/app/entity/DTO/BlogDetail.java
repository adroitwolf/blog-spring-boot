package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 16:07
 * Description: :博客内容的详细内容
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlogDetail  extends Blog{

    private String content;

    private String contentMd;


}
