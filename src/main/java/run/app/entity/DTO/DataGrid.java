package run.app.entity.DTO;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/26 9:06
 * Description: ://TODO ${END}
 */
@Data
@ToString
public class DataGrid implements Serializable {

    private long total;

    private List<?> rows;
}
