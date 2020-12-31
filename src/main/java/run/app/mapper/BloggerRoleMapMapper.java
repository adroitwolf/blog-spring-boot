package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerRoleMapExample;
import run.app.entity.model.BloggerRoleMapKey;

public interface BloggerRoleMapMapper extends BaseMapper<BloggerRoleMapKey, BloggerRoleMapKey> {
    long countByExample(BloggerRoleMapExample example);

    int deleteByExample(BloggerRoleMapExample example);

    List<BloggerRoleMapKey> selectByExample(BloggerRoleMapExample example);

    int updateByExampleSelective(@Param("record") BloggerRoleMapKey record, @Param("example") BloggerRoleMapExample example);

    int updateByExample(@Param("record") BloggerRoleMapKey record, @Param("example") BloggerRoleMapExample example);
}