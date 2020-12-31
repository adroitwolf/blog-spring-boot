package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import run.app.entity.DTO.UserInfo;
import run.app.entity.VO.QueryParams;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;

public interface BloggerAccountMapper extends BaseMapper<BloggerAccount, Long> {
    long countByExample(BloggerAccountExample example);

    int deleteByExample(BloggerAccountExample example);

    List<BloggerAccount> selectByExample(BloggerAccountExample example);

    int updateByExampleSelective(@Param("record") BloggerAccount record, @Param("example") BloggerAccountExample example);

    int updateByExample(@Param("record") BloggerAccount record, @Param("example") BloggerAccountExample example);


    List<UserInfo> selectByQueryParams(@Param("query") QueryParams postQueryParams);
}