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

//    针对前台博客卡片详细内容带作者的
    public BlogDetailWithAuthor(Long id,String title, String summary,
                                 Date releaseDate,
                                 List<String> tagTitle,
                                String content,  String picture,String username, String avatarId) {
        super(id, title, summary,releaseDate,tagTitle,picture, content);
        this.username = username;
        this.avatarId = avatarId;
    }
}
