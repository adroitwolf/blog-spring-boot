package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/31 10:23
 * Description: 返回给前台的评论结构
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long root;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private UserDTO user;

}
