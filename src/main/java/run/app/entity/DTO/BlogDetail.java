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

//    private String contentMd;

    //    public BlogDetail() {
//    }

//    准对前台将status属性去掉
//    public BlogDetail(Long id, String title, String summary, Date releaseDate, List<String> tagsTitle, String picture,String content) {
//        super(id,title,summary,releaseDate,tagsTitle,picture);
//        this.content = content;
//    }

//    public BlogDetail(Long id, String status, String title, String summary, Date releaseDate, Date nearestModifyDate, List<String> tagTitle, String picture,String content, String contentMd) {
//        super(id,status,title,summary,releaseDate,nearestModifyDate,tagTitle,picture);
//        this.content = content;
//        this.contentMd = contentMd;
//    }
}
