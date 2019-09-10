package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BlogLabel;
import run.app.entity.model.BlogLabelExample;

public interface BlogLabelMapper {
    long countByExample(BlogLabelExample example);

    int deleteByExample(BlogLabelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BlogLabel record);

    int insertSelective(BlogLabel record);

    List<BlogLabel> selectByExample(BlogLabelExample example);

    /* 增加通过内容查找id的代码*/
    String selectByExampleForPrimaryKey(BlogLabelExample example);

    String selectByExampleForTitleByPrimaryKey(Long id);



    int updateByPrimaryKeyForReduceNum(Long id);

    int updateByPrimaryKeyForAddNum(Long id);



    /*增加代码结束*/


    BlogLabel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BlogLabel record, @Param("example") BlogLabelExample example);

    int updateByExample(@Param("record") BlogLabel record, @Param("example") BlogLabelExample example);

    int updateByPrimaryKeySelective(BlogLabel record);

    int updateByPrimaryKey(BlogLabel record);
}