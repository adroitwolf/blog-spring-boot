package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.Blog;
import run.app.entity.model.BlogExample;
import run.app.entity.model.BlogWithBLOBs;

public interface BlogMapper {
    long countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlogWithBLOBs record);

    int insertSelective(BlogWithBLOBs record);

    List<BlogWithBLOBs> selectByExampleWithBLOBs(BlogExample example);

    List<Blog> selectByExample(BlogExample example);

    BlogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlogWithBLOBs record, @Param("example") BlogExample example);

    int updateByExampleWithBLOBs(@Param("record") BlogWithBLOBs record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(BlogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BlogWithBLOBs record);

    int updateByPrimaryKey(Blog record);
}