package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/14 16:07
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class BlogDetail {

    public BlogDetail() {
    }

//    准对前台将status属性去掉
    public BlogDetail(Integer id, String title,  Date releaseDate,  String tagTitle, String content) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.tagTitle = tagTitle;
        this.content = content;
    }

    public BlogDetail(Integer id, String status, String title, String summary, Date releaseDate, Date nearestModifyDate, String tagTitle, String content, String contentMd) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.nearestModifyDate = nearestModifyDate;
        this.tagTitle = tagTitle;
        this.content = content;
        this.contentMd = contentMd;
    }

    private Integer id;


    private String status;

    private String title;

    private String summary;

    @DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date releaseDate;

    @DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date nearestModifyDate;

    private String tagTitle;

    private String content;

    private String contentMd;



}
