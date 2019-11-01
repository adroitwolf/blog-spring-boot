package run.app.entity.enums.converter;

import org.springframework.stereotype.Component;
import run.app.entity.enums.ArticleStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/18 19:00
 * Description: 博客状态转换器
 */
@Component
public class ArticleStatusConverter extends BaseConverter<ArticleStatus,String>{
    public ArticleStatusConverter() {
        super(ArticleStatus.class);
    }


}
