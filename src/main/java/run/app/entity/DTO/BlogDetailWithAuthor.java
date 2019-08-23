package run.app.entity.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 19:48
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class BlogDetailWithAuthor extends BlogDetail {

    private String username;

    private String avatarId;


    public BlogDetailWithAuthor() {
    }

    public BlogDetailWithAuthor(Integer id,String title, String summary,
                                 Date releaseDate,
                                 List<String> tagTitle,
                                String content,  String username, String avatarId) {
        super(id, title, summary,releaseDate, tagTitle, content);
        this.username = username;
        this.avatarId = avatarId;
    }
}
