package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import run.app.entity.VO.PostQueryParams;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogExample;

public interface BlogMapper extends BaseMapper<Blog,Long>{
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    List<Blog> selectByExample(BlogExample example);

    List<Blog> selectByUserExample(@Param("query") PostQueryParams postQueryParams, @Param("blogger_id") Long blogger_id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    void deletePicByPicId(Long picId);

    @Select("SELECT id,title FROM blog WHERE ID = #{blogId}")
    String selectBlogNameByPrimaryKey(Long blogId);
}