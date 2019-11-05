package run.app.BeanTest.enums;

import org.junit.Test;
import run.app.entity.enums.ArticleStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:33
 * Description: 文章状态测试
 */
public class ArticleStatusTest {

    @Test
    public void enmuTest(){

        ArticleStatus articleStatus;
        articleStatus = ArticleStatus.PUBLISHED;
        System.out.println(articleStatus.getName());
    }
}
