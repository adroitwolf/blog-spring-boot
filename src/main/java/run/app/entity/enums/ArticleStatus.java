package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/18 18:56
 * Description: 博客状态
 */
public enum ArticleStatus implements BaseEnum<String> {

    PUBLISHED,
    RECYCLE;

    public String getName() {
        return name();
    }
}
