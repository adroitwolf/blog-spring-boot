package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/23 17:39
 * Description: 用户角色模型
 */
public enum Role implements BaseEnum<String>{
    ADMIN,
    USER;


    public String getAuthority() {
        return name();
    }


}
