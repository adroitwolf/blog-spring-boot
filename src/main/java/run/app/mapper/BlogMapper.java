package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogExample;
import run.app.entity.params.PostQueryParams;

public interface BlogMapper {
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExample(BlogExample example);

    List<Blog> selectByUserExample(@Param("query") PostQueryParams postQueryParams, @Param("blogger_id") Integer blogger_id);

    Blog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);
}