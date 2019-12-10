package run.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.model.BlogLog;
import run.app.mapper.BlogLogMapper;
import run.app.service.LogService;
import run.app.util.AppUtil;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:30
 * Description:日志实现类
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    BlogLogMapper blogLogMapper;

    @Override
    public void storageLog(BlogLog blogLog) {
        blogLog.setId(AppUtil.nextId());
        blogLogMapper.insertSelective(blogLog);
    }
}
