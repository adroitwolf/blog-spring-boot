package run.app.BeanTest.enums;

import org.junit.Test;
import run.app.entity.enums.Role;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:28
 * Description:测试Role枚举
 */
public class RoleTest {


    @Test
    public void getName(){
        System.out.println(Role.ADMIN.getAuthority());
    }



}
