package run.app.mapper;


/**
 * mapper的底层接口层
 */

public interface BaseMapper<T, PK> {

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 新增非空字段
     *
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主键搜索
     *
     * @param pk
     * @return
     */
    T selectByPrimaryKey(PK pk);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据非空字段更新tus
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);


    /**
     * 根据主键删除数据
     *
     * @param pk
     * @return
     */
    int deleteByPrimaryKey(PK pk);
}
