package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.service.QMailService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/3/10 11:09
 * Description: 邮箱接口测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailServiceTest {

    @Autowired
    QMailService qMailService;


    @Test
    public void send() {
        qMailService.sendSimpleMail("1363531458@qq.com", "测试邮件", "测试邮件发送成功");
    }
}
