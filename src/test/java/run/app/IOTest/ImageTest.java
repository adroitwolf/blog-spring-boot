package run.app.IOTest;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/11/10 15:46
 * Description: 测试一下File下面的APi
 */
public class ImageTest {
    private File file;

    @Before
    public void init(){
        file = new File("D:\\code\\java\\eblog\\img\\591875d595d14764af0ace1a217f78b2.jpg");
    }

    @Test
    public void testApi(){
        System.out.println(file.getParent());
    }

}
