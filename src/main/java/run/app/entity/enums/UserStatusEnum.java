package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/22 20:04
 * Description: 用户状态
 */
public enum UserStatusEnum implements BaseEnum<String>{
    YES,
    NO;
    public String getName() {
        return name();
    }
}
