package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerPicture;
import run.app.entity.model.BloggerPictureExample;

public interface BloggerPictureMapper extends BaseMapper<BloggerPicture,Long> {
    long countByExample(BloggerPictureExample example);

    int deleteByExample(BloggerPictureExample example);

    List<BloggerPicture> selectByExample(BloggerPictureExample example);

    int updateByExampleSelective(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    int updateByExample(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

}