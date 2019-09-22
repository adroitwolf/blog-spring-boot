package run.app.entity.DTO;


import lombok.Data;

/**
 * User:WHOAMI
 * Date:22-9-2019
 * Description: 图像文件详细信息
 */

@Data
public class ImageFile {

    private String fileName;

    private Integer width;

    private Integer height;
}
