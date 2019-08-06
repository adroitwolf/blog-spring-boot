package run.app.security.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.entity.model.BlogLog;
import run.app.entity.model.BlogLogExample;
import run.app.mapper.BlogLogMapper;
import run.app.security.log.LogService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/30 16:30
 * Description: ://TODO ${END}
 */
@Service
public class LogServiceImpl implements LogService {


    @Autowired
    BlogLogMapper blogLogMapper;


    @Override
    public void storageLog(BlogLog blogLog) {
        BlogLogExample blogLogExample = new BlogLogExample();
        BlogLogExample.Criteria criteria = blogLogExample.createCriteria();
        blogLogMapper.insertSelective(blogLog);
    }
}
