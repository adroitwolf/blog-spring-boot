package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.DTO.ClickStatus;
import run.app.entity.DTO.PopularBlog;
import run.app.entity.model.BlogStatus;
import run.app.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/28 22:49
 * Description:redis服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    RedisService redisService;

    @Test
    public void getTest(){
        redisService.incrementBlogClickedCount(new ClickStatus(1234L,"123","123",1));
        Integer countFrmRedis = redisService.getBlogClickedCount(123L);
        Assert.assertNotNull(countFrmRedis);
        System.out.println(countFrmRedis);
        Assert.assertNull(redisService.getBlogClickedCount(12L));

    }

    @Test
    public void testListTop5(){
        Set<PopularBlog> statuses = redisService.listTop5FrmRedis();
        System.out.println(statuses.size());
        statuses.stream().forEach(x-> System.out.println(x));
    }

    @Test
    public void testTop5(){
        List<PopularBlog> list = new ArrayList<>();

        for(int i=0;i<5;i++){
            PopularBlog popularBlog = new PopularBlog();
            popularBlog.setId((long) i);
            popularBlog.setBlogName(String.valueOf(i));
            popularBlog.setClickcount(RandomUtils.nextInt(10,100));
            list.add(popularBlog);
        }
        redisService.transTop5Posts2Redis(list);


    }
}
