package run.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import run.app.entity.VO.AttachmentQueryParams;
import run.app.entity.model.BloggerPicture;
import run.app.entity.model.BloggerPictureExample;

public interface BloggerPictureMapper extends BaseMapper<BloggerPicture, Long> {
    long countByExample(BloggerPictureExample example);

    int deleteByExample(BloggerPictureExample example);

    List<BloggerPicture> selectByExample(BloggerPictureExample example);

    int updateByExampleSelective(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    int updateByExample(@Param("record") BloggerPicture record, @Param("example") BloggerPictureExample example);

    List<BloggerPicture> selectPictureByExample(@Param("query") AttachmentQueryParams attachmentQueryParams, @Param("userId") Long blogger_id);


    @Select("select DISTINCT media_type from blogger_picture WHERE BLOGGER_ID = #{userId}")
    List<String> findAllMediaType(@Param("userId") Long userId);

    @Update("update blogger_picture set CITE_NUM = CITE_NUM + 1 where ID = #{Id}")
    void updatePictureByAddCiteNum(@Param("Id") Long id);

    @Update("update blogger_picture set CITE_NUM = CITE_NUM - 1 where ID = #{Id}")
    void updatePictureByReduceCiteNum(@Param("Id") Long id);
}