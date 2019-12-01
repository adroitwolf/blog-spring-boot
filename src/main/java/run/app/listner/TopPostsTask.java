package run.app.listner;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import run.app.service.BlogStatusService;
import run.app.service.RedisService;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/30 20:21
 * Description: 博客排名定时任务
 */

public class TopPostsTask extends QuartzJobBean {

    @Autowired
    BlogStatusService blogStatusService;

    @Autowired
    RedisService redisService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        redisService.transTop5Posts2Redis(blogStatusService.listTop5Posts());
    }
}
