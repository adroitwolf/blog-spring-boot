package run.app.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import run.app.entity.model.BloggerPicture;
import run.app.entity.model.BloggerPictureExample;

public interface BloggerPictureMapper {
    long countByExample(BloggerPictureExample example);

    int deleteByExample(BloggerPictureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BloggerPicture record);

    int insertSelective(BloggerPicture record);

    List<BloggerPicture> selectByExampleWithBLOBs(BloggerPictureExample example);

    List<BloggerPicture> selectByExample(BloggerPictureExample example);

    BloggerPicture selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    int updateByExampleWithBLOBs(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    int updateByExample(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    int updateByPrimaryKeySelective(BloggerPicture record);

    int updateByPrimaryKeyWithBLOBs(BloggerPicture record);

    int updateByPrimaryKey(BloggerPicture record);
}