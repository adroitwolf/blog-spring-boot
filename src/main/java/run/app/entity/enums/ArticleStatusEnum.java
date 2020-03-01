package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/18 18:56
 * Description: 博客状态
 */
public enum ArticleStatusEnum implements BaseEnum<String> {

    PUBLISHED, //审核成功
    CHECK, //审核中
    NO, //审核失败
    RECYCLE; //回收站

    public String getName() {
        return name();
    }
}
