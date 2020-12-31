package run.app.BeanTest.enums;

import org.junit.Test;
import run.app.entity.enums.ArticleStatusEnum;
import run.app.entity.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:33
 * Description: 文章状态测试
 */
public class ArticleStatusTest {

    @Test
    public void enmuTest() {

        ArticleStatusEnum articleStatus;
        articleStatus = ArticleStatusEnum.PUBLISHED;
        System.out.println(articleStatus.getName());
    }

    @Test
    public void valueTest() {
        ArticleStatusEnum.valueOf("CHECK");
    }

    @Test
    public void equalTest() {
        Long userId = 412727960840175616L;
        List<RoleEnum> roles = new ArrayList<>();
        roles.add(RoleEnum.USER);
        roles.add(RoleEnum.ADMIN);
        String status = roles.contains(RoleEnum.ADMIN) ? ArticleStatusEnum.PUBLISHED.getName() : ArticleStatusEnum.CHECK.getName();
        System.out.println(status);
    }
}
