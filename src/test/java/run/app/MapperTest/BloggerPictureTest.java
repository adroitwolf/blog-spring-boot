package run.app.MapperTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.mapper.BloggerPictureMapper;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/6 22:01
 * Description: 测试博客图片mapper层
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BloggerPictureTest {

    @Autowired
    BloggerPictureMapper bloggerPictureMapper;

    @Test
    public void findAllMediaType(){
        System.out.println(bloggerPictureMapper.findAllMediaType(387193717087272960L));
    }

    @Test
    public void updatePictureByAddCiteNum(){
    }
}
