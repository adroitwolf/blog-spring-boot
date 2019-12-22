package run.app.listner;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import run.app.service.BlogStatusService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 14:06
 * Description: 文章点击量定时任务
 */
@Slf4j
public class ClickBlogTask extends QuartzJobBean {

    @Autowired
    BlogStatusService blogStatusService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss hh:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        blogStatusService.transClickedCountFromRedis2DB();
        log.info("ClickTask收录-------- {}", sdf.format(new Date()));
    }
}
