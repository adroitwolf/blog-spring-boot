package run.app.entity.enums.converter;

import org.springframework.stereotype.Component;
import run.app.entity.enums.UserStatusEnum;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/22 20:05
 * Description: 用户状态枚举转换类
 */
@Component
public class UserStatusConverter  extends BaseConverter<UserStatusEnum,String>{
    public UserStatusConverter() {
        super(UserStatusEnum.class);
    }
}
