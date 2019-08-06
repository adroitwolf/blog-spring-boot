package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogLog;
import run.app.entity.model.BlogLogExample;

public interface BlogLogMapper {
    long countByExample(BlogLogExample example);

    int deleteByExample(BlogLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlogLog record);

    int insertSelective(BlogLog record);

    List<BlogLog> selectByExample(BlogLogExample example);

    BlogLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlogLog record, @Param("example") BlogLogExample example);

    int updateByExample(@Param("record") BlogLog record, @Param("example") BlogLogExample example);

    int updateByPrimaryKeySelective(BlogLog record);

    int updateByPrimaryKey(BlogLog record);
}