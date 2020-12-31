package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogLog;
import run.app.entity.model.BlogLogExample;

public interface BlogLogMapper extends BaseMapper<BlogLog, Long> {
    long countByExample(BlogLogExample example);

    int deleteByExample(BlogLogExample example);

    List<BlogLog> selectByExample(BlogLogExample example);

    int updateByExampleSelective(@Param("record") BlogLog record, @Param("example") BlogLogExample example);

    int updateByExample(@Param("record") BlogLog record, @Param("example") BlogLogExample example);

}