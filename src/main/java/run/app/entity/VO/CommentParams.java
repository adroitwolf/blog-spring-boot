package run.app.entity.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/26 10:34
 * Description: 评论实体
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentParams {


    @JsonSerialize(using = ToStringSerializer.class)
    @Min(value = 0,message = "请不要进行非法操作")
    private Long objectId;

    @JsonSerialize(using = ToStringSerializer.class)
    @Min(value = 0,message = "请不要进行非法操作")
    private Long toId;


    @JsonSerialize(using = ToStringSerializer.class)
    @Min(value = 0,message = "请不要进行非法操作")
    private Long root = 0L;

    @NonNull
    private String type;


    @Size(max = 1023,message = "评论长度不能超过{max}")
    private String content;


    @JsonSerialize(using = ToStringSerializer.class)
    @Min(value = 0,message = "不要进行非法操作")
    private Long pid = 0L;



}
