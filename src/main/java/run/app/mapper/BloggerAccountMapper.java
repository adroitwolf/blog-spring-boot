package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;

public interface BloggerAccountMapper {
    long countByExample(BloggerAccountExample example);

    int deleteByExample(BloggerAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BloggerAccount record);

    int insertSelective(BloggerAccount record);

    List<BloggerAccount> selectByExample(BloggerAccountExample example);

    BloggerAccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BloggerAccount record, @Param("example") BloggerAccountExample example);

    int updateByExample(@Param("record") BloggerAccount record, @Param("example") BloggerAccountExample example);

    int updateByPrimaryKeySelective(BloggerAccount record);

    int updateByPrimaryKey(BloggerAccount record);
}