package run.app.entity.DTO;


import lombok.Data;
import org.springframework.http.MediaType;

import java.util.Date;

/**
 * User:WHOAMI
 * Date:22-9-2019
 * Description: 图像文件详细信息
 */

@Data
public class ImageFile {

    private String path;

    private String fileKey;

    private String title;

    private String suffx;

    private Long size;

    private Integer width;

    private Integer height;

    private String thumbPath;

    private MediaType mediaType;
}
