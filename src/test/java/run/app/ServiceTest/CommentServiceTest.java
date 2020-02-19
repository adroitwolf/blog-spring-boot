package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.entity.DTO.BaseResponse;
import run.app.entity.VO.PageInfo;
import run.app.service.CommentService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/2/1 14:07
 * Description: 评论服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CommentServiceTest {


    @Autowired
    CommentService commentService;


    @Test
    public void getList(){

        BaseResponse list = commentService.getList(413118736350838784L, "BLOG_COMMENT", new PageInfo(5, 1, null, null));
        log.info(list.toString());

    }

}
