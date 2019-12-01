package run.app.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.app.listner.ClickBlogTask;
import run.app.listner.TopPostsTask;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/29 14:03
 * Description: Quartz配置文件
 */
@Configuration
public class QuartzConfig {
    private static final String CLICK_TASK_IDENTITY = "ClickTaskQuartz";
    private static final String TOP_POST_TASK_IDENTITY = "TopPostQuartz";

    @Bean
    public JobDetail clickQuartzDetail(){
        return JobBuilder.newJob(ClickBlogTask.class).withIdentity(CLICK_TASK_IDENTITY).storeDurably().build();
    }


    @Bean
    public Trigger clickQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(1)//10c/s
                .repeatForever();  //一直执行
        return TriggerBuilder.newTrigger().forJob(clickQuartzDetail())
                .withIdentity(CLICK_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }


    @Bean
    public JobDetail topPostsQuartzDetail(){
        return JobBuilder.newJob(TopPostsTask.class).withIdentity(TOP_POST_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public Trigger topPostQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24)//1d
                .repeatForever();  //一直执行
        return TriggerBuilder.newTrigger().forJob(topPostsQuartzDetail())
                .withIdentity(TOP_POST_TASK_IDENTITY)
                .withSchedule(scheduleBuilder)
                .build();
    }

}
