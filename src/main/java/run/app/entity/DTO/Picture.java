package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/9/3 23:05
 * Description: ://TODO ${END}
 */
@NoArgsConstructor
@Data
@ToString
public class Picture {

    private Integer id;

    private String title;


    private String bewrite;

    public Picture(Integer id, String title, String bewrite) {
        this.id = id;
        this.title = title;
        this.bewrite = bewrite;
    }
}
