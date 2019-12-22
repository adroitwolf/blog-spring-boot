package run.app.BeanTest.enums;

import org.junit.Test;
import run.app.entity.enums.ArticleStatusEnum;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:33
 * Description: 文章状态测试
 */
public class ArticleStatusTest {

    @Test
    public void enmuTest(){

        ArticleStatusEnum articleStatus;
        articleStatus = ArticleStatusEnum.PUBLISHED;
        System.out.println(articleStatus.getName());
    }
}
