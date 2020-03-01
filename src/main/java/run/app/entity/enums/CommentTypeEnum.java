package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/1/26 11:17
 * Description: 评论类型 0-博客，1-评论，2-留言
 */
public enum CommentTypeEnum implements BaseEnum<Byte>{

    BLOG_COMMENT((byte)1),
    COMMENT_COM((byte)2);

    private final Byte value;

    CommentTypeEnum(Byte value){
        this.value = value;
    }


    public Byte getValue() {
        return value;
    }
}
