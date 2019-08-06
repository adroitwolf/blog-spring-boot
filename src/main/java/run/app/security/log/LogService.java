package run.app.security.log;

import org.springframework.beans.factory.annotation.Autowired;
import run.app.entity.model.BlogLog;
import run.app.mapper.BlogLogMapper;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:27
 * Description: ://TODO ${END}
 */
public interface LogService {




    void storageLog(BlogLog blogLog);

}
