package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.enums.ArticleStatusEnum;
import run.app.service.ArticleService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 22:53
 * Description: 文章服务控制层
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test
    public void test() {
        System.out.println(ArticleStatusEnum.valueOf("admin"));

    }

}
