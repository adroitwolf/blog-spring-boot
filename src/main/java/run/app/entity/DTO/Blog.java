package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/8/23 22:10
 * Description: :博客对象的实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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

    private String picture;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long pictureId;
}
