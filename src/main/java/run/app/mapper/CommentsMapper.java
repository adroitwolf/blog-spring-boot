package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.Comments;
import run.app.entity.model.CommentsExample;

public interface CommentsMapper extends BaseMapper<Comments,Long>{
    long countByExample(CommentsExample example);

    int deleteByExample(CommentsExample example);

    List<Comments> selectByExample(CommentsExample example);

    int updateByExampleSelective(@Param("record") Comments record, @Param("example") CommentsExample example);

    int updateByExample(@Param("record") Comments record, @Param("example") CommentsExample example);

}