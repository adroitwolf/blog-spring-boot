package run.app.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/8 20:27
 * Description: 四种操作 CRUD
 */
public enum Opertaion {
    CREATE(379043768654888960L,"新增","新增操作"),
    RETRIEVE(379043849965666304L,"查询","查询操作"),
    UPDATE(379043900142125056L,"更新","更新操作"),
    DETELE(379043931318386688L,"删除","删除操作");

    private final long id;
    private final String name;
    private final String des;

    Opertaion(long id,String name,String des) {
        this.id = id;
        this.name = name;
        this.des = des;

    }

    public long Id() {
        return id;
    }

    public String Name() {
        return name;
    }

    public String Des() {
        return des;
    }
}
