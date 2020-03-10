package run.app.BeanTest.Util;

import org.junit.Test;
import run.app.util.AppUtil;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 20:34
 * Description: 应用包测试
 */
public class AppUtilTest {

    @Test
    public void IdTest(){

        System.out.println(AppUtil.nextId());
    }


    @Test
    public void codeTest(){
        System.out.println(AppUtil.getCode());
    }
}
