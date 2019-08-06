package run.app.entity.DTO;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 12:01
 * Description: ://TODO ${END}
 */
@Data

public class BaseResponse {
    private Integer status;
    private String Message;
    private Object data;

}
