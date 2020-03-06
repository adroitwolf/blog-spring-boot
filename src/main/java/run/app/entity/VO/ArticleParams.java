package run.app.entity.VO;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 6:14
 * Description:文章参数
 */

@Data
@ToString
public class ArticleParams {
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 30,message = "标题字数不能超过{max}")
    private String title;



    @NotBlank(message = "文章内容不能为空")
    private String contentMd;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    private Long pictureId;

    private List<String> tagList;

//    private Integer tag;

    @NotBlank(message = "文章总结不能为空")
    @Size(max=100,message = "文章总结不能超过{maxz}")
    private String summary;




}
