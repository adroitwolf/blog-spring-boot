package run.app.ServiceTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.exception.BadRequestException;
import run.app.service.AccountService;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/12/15 14:19
 * Description: 账号服务层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void BlockTest(){
        expectedEx.expect(BadRequestException.class);
        expectedEx.expectMessage("账号未找到");
        accountService.updateUserStatus(123L,"YES");
        log.debug(accountService.updateUserStatus(400710202904543232L,"NO").toString());
    }


    @Test
    public void deleteUser(){
        expectedEx.expect(BadRequestException.class);
        expectedEx.expectMessage("账号未找到");
        accountService.deleteUser(123L);
    }

    @Test
    public void queryUser(){

    }

}
