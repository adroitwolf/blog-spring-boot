package run.app.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/16 18:46
 * Description: :用户上传图片的详细信息
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureInfo extends Picture {

    private Date uploadDate;

    private String suffx;

    private Long size;

    private Integer width;

    private Integer height;

    private Integer citeNum;

    private String mediaType;

}
