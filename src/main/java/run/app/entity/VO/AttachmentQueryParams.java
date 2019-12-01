package run.app.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/13 13:41
 * Description: 附件查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentQueryParams {

    private String keywords;

    private String mediaType;
}
