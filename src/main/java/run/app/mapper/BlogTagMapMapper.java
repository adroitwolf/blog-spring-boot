package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogTagMapExample;
import run.app.entity.model.BlogTagMapKey;

public interface BlogTagMapMapper extends BaseMapper<BlogTagMapKey, BlogTagMapKey> {
    long countByExample(BlogTagMapExample example);

    int deleteByExample(BlogTagMapExample example);

    /* 增加两条返回单一子段的代码  8-23-19 WHOAMI*/
    List<Long> selectByExampleForTag(BlogTagMapExample example);

    List<Long> selectByExampleForBlogId(BlogTagMapExample example);
    /* 增加代码结束*/

    List<BlogTagMapKey> selectByExample(BlogTagMapExample example);

    int updateByExampleSelective(@Param("record") BlogTagMapKey record, @Param("example") BlogTagMapExample example);

    int updateByExample(@Param("record") BlogTagMapKey record, @Param("example") BlogTagMapExample example);
}