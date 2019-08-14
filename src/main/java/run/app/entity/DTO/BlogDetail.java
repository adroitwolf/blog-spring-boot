package run.app.entity.DTO;

import lombok.Data;
import lombok.ToString;

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

    private Integer id;


    private String status;

    private String title;

    private String summary;

    private Date releaseDate;

    private Date nearestModifyDate;

    private String tagTitle;

    private String content;

    private String contentMd;



}
