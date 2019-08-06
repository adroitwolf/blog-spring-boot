package run.app.app;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.DTO.DataGrid;
import run.app.service.ArticleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppApplicationTests {

    @Autowired
    ArticleService articleService;

    @Test
    public void contextLoads() {

        @NonNull DataGrid articleList = articleService.getArticleList(1, 5, "020ee66c0db584fb8fa4da7822a7e0ca");
        log.debug(String.valueOf(articleList.getTotal()));


    }

}
