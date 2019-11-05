package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerRole;
import run.app.entity.model.BloggerRoleExample;

public interface BloggerRoleMapper extends BaseMapper<BloggerRole,Long>{
    long countByExample(BloggerRoleExample example);

    int deleteByExample(BloggerRoleExample example);

    List<BloggerRole> selectByExample(BloggerRoleExample example);

    int updateByExampleSelective(@Param("record") BloggerRole record, @Param("example") BloggerRoleExample example);

    int updateByExample(@Param("record") BloggerRole record, @Param("example") BloggerRoleExample example);

}