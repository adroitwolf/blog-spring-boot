package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.DTO.BaseResponse;
import run.app.service.PostService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/30 20:16
 * Description: 前台服务类测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    public void listTop5Test() {
        BaseResponse posts = postService.getTopPosts();
        System.out.println(posts);
    }
}
