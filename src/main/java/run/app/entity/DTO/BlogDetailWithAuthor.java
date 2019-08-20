package run.app.entity.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

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

    public BlogDetailWithAuthor(Integer id,String title,
                                 Date releaseDate,
                                 String tagTitle,
                                String content,  String username, String avatarId) {
        super(id, title, releaseDate, tagTitle, content);
        this.username = username;
        this.avatarId = avatarId;
    }
}
