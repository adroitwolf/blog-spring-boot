package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogContent;
import run.app.entity.model.BlogContentExample;

public interface BlogContentMapper extends BaseMapper<BlogContent, Long> {
    long countByExample(BlogContentExample example);

    int deleteByExample(BlogContentExample example);

    List<BlogContent> selectByExampleWithBLOBs(BlogContentExample example);

    List<BlogContent> selectByExample(BlogContentExample example);

    int updateByExampleSelective(@Param("record") BlogContent record, @Param("example") BlogContentExample example);

    int updateByExampleWithBLOBs(@Param("record") BlogContent record, @Param("example") BlogContentExample example);

    int updateByExample(@Param("record") BlogContent record, @Param("example") BlogContentExample example);


}