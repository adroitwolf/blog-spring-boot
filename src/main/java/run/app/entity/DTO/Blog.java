package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 22:10
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class Blog {

    public Blog() {
    }

    public Blog(Integer id, String status, String title, String summary, Date releaseDate, Date nearestModifyDate, List<String> tagsTitle) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.nearestModifyDate = nearestModifyDate;
        this.tagsTitle = tagsTitle;
    }

    public Blog(Integer id, String title, String summary, Date releaseDate, List<String> tagsTitle) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.tagsTitle = tagsTitle;
    }

    public Blog(Integer id, String status, String title, String summary, Date releaseDate, Date nearestModifyDate, Integer tagTitle) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.nearestModifyDate = nearestModifyDate;
        this.tagsTitle = tagsTitle;
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

    private List<String> tagsTitle;

}
