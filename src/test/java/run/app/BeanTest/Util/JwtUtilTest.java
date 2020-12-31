package run.app.BeanTest.Util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import run.app.config.properties.JWTProperties;
import run.app.entity.DTO.User;
import run.app.entity.enums.RoleEnum;
import run.app.service.TokenService;
import run.app.util.JwtUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/31 8:25
 * Description: jwt工具包测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JwtUtilTest {

    @Autowired
    JWTProperties jwtProperties;

    @Autowired
    TokenService tokenService;

    @Test
    public void jwtPropertiesTest() {


        List<String> roles = new ArrayList<String>() {
            {
                add(RoleEnum.ADMIN.name());
            }
        };


        System.out.println(JwtUtil.generateToken(new User(1L, "zzz", roles)));
    }


    @Test
    public void jwtVerfity() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiMSIsInp6eiJdLCJyb2xlcyI6WyJBRE1JTiJdLCJpc3MiOiJXSE9BTUkiLCJleHAiOjE1NzI1MTEyMTgsInVzZXJJZCI6MX0.P0xV-qTEn5toukrO1poaoDzFQ-IVxPEGKzjKu0EcGGo";
        JwtUtil.generateRole(token).stream().forEach(x -> System.out.println(x));
    }
}
