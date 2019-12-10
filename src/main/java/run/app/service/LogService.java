package run.app.service;

import run.app.entity.model.BlogLog;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:27
 * Description: 日志监控
 */
public interface LogService {

    void storageLog(BlogLog blogLog);

}
