package run.app.BeanTest.enums;

import org.assertj.core.util.Lists;
import org.junit.Test;
import run.app.entity.enums.RoleEnum;
import run.app.entity.enums.UserStatusEnum;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:28
 * Description:测试Role枚举
 */
public class RoleTest {


    @Test
    public void getName() {
        System.out.println(RoleEnum.ADMIN.getAuthority());
    }

    @Test
    public void lamTest() {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);

        list.stream().filter(Objects::isNull).forEach(x -> System.out.println(x));
    }


    @Test
    public void equalTest() {
        String ENABLED = "YES";
        System.out.println(UserStatusEnum.YES.getName().equals(ENABLED));
    }

}
