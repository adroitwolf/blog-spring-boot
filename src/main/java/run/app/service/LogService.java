package run.app.service;

import run.app.entity.model.BlogLog;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:27
 * Description: 日志监控
 */
public interface LogService {

    /**
     * 功能描述: 存储日志
     *
     * @Param: [blogLog]
     * @Return: void
     * @Author: WHOAMI
     * @Date: 2020/1/30 19:43
     */
    void storageLog(BlogLog blogLog);

}
