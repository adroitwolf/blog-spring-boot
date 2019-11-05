package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 12:01
 * Description: 系统默认返回json值
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private Integer status;
    private String Message;
    private Object data;
}
