package run.app.app;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.service.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    public void contextLoads() {



    }

}
