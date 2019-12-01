package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogStatus;
import run.app.entity.model.BlogStatusExample;

public interface BlogStatusMapper extends BaseMapper<BlogStatus,Long> {
    long countByExample(BlogStatusExample example);

    int deleteByExample(BlogStatusExample example);

    List<BlogStatus> selectByExample(BlogStatusExample example);

    int updateByExampleSelective(@Param("record") BlogStatus record, @Param("example") BlogStatusExample example);

    int updateByExample(@Param("record") BlogStatus record, @Param("example") BlogStatusExample example);


}