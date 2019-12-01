package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 16:57
 * Description: 点赞状态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClickStatus {
    private Long blogId;
    private String remoteIp;
    private String sessionId;
    private Integer count;
}
