package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerProfile;
import run.app.entity.model.BloggerProfileExample;

public interface BloggerProfileMapper extends BaseMapper<BloggerProfile,Long>{
    long countByExample(BloggerProfileExample example);

    int deleteByExample(BloggerProfileExample example);

    List<BloggerProfile> selectByExample(BloggerProfileExample example);

    int updateByExampleSelective(@Param("record") BloggerProfile record, @Param("example") BloggerProfileExample example);

    int updateByExample(@Param("record") BloggerProfile record, @Param("example") BloggerProfileExample example);
}