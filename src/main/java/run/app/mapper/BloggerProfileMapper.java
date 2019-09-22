package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerProfile;
import run.app.entity.model.BloggerProfileExample;
import run.app.entity.model.BloggerProfileWithBLOBs;

public interface BloggerProfileMapper {
    long countByExample(BloggerProfileExample example);

    int deleteByExample(BloggerProfileExample example);

    int deleteByPrimaryKey(Long bloggerId);

    int insert(BloggerProfileWithBLOBs record);

    int insertSelective(BloggerProfileWithBLOBs record);

    List<BloggerProfileWithBLOBs> selectByExampleWithBLOBs(BloggerProfileExample example);

    List<BloggerProfile> selectByExample(BloggerProfileExample example);

    BloggerProfileWithBLOBs selectByPrimaryKey(Long bloggerId);

    int updateByExampleSelective(@Param("record") BloggerProfileWithBLOBs record, @Param("example") BloggerProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") BloggerProfileWithBLOBs record, @Param("example") BloggerProfileExample example);

    int updateByExample(@Param("record") BloggerProfile record, @Param("example") BloggerProfileExample example);

    int updateByPrimaryKeySelective(BloggerProfileWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BloggerProfileWithBLOBs record);

    int updateByPrimaryKey(BloggerProfile record);
}