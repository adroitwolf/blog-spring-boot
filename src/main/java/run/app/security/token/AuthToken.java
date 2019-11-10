package run.app.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 11:06
 * Description:自定义token实体类
 */

@Data
@ToString
public class AuthToken {



    private String accsess_token;

//    private String refresh_token;

    private long expire_in;

}
