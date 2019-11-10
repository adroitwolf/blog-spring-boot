package run.app.security.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.model.BlogLog;
import run.app.entity.model.BlogLogExample;
import run.app.mapper.BlogLogMapper;
import run.app.security.log.LogService;
import run.app.util.AppUtil;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:30
 * Description:日志实现类
 */
@Service
public class LogServiceImpl implements LogService {


    public LogServiceImpl() {
        this.appUtil = AppUtil.getInstance();
    }

    @Autowired
    BlogLogMapper blogLogMapper;


    AppUtil appUtil;

    @Override
    public void storageLog(BlogLog blogLog) {
        blogLog.setId(appUtil.nextId());
        blogLogMapper.insertSelective(blogLog);
    }
}
