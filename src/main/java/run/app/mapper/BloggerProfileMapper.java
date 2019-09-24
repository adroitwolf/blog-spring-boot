package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerProfile;
import run.app.entity.model.BloggerProfileExample;
import run.app.entity.model.BloggerProfileWithBLOBs;

public interface BloggerProfileMapper extends BaseMapper<BloggerProfileWithBLOBs,Long>{
    long countByExample(BloggerProfileExample example);

    int deleteByExample(BloggerProfileExample example);

    List<BloggerProfileWithBLOBs> selectByExampleWithBLOBs(BloggerProfileExample example);

    List<BloggerProfile> selectByExample(BloggerProfileExample example);

    int updateByExampleSelective(@Param("record") BloggerProfileWithBLOBs record, @Param("example") BloggerProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") BloggerProfileWithBLOBs record, @Param("example") BloggerProfileExample example);

    int updateByExample(@Param("record") BloggerProfile record, @Param("example") BloggerProfileExample example);


    int updateByPrimaryKeyWithBLOBs(BloggerProfileWithBLOBs record);

}