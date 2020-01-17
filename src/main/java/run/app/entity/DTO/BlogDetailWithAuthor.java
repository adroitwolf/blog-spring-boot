package run.app.entity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
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
public class BlogDetailWithAuthor extends BlogDetail {

    private String username;

    private String avatarId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long bloggerId;


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
