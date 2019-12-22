package run.app.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.app.config.properties.JWTProperties;
import run.app.entity.DTO.User;
import run.app.entity.enums.RoleEnum;
import run.app.exception.UnAccessException;
import run.app.service.TokenService;
import run.app.service.AccountService;
import run.app.service.UserService;
import run.app.util.JwtUtil;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 16:30
 * Description: 令牌实现类
 */

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {


    @Autowired
    UserService userService;


    @Autowired
    AccountService accountService;

    @Autowired
    JWTProperties jwtProperties;



    @Override
    public void authentication(Long id, String token) {
        if(null == id || !id.equals(getUserIdWithToken(token))){

            throw new UnAccessException("您没有权限进行该操作");
        }
    }

    //    利用Jwt生成token
    @Override
    public String getToken(User user) {
        return JwtUtil.generateToken(user);
    }

    @Override
    public boolean verifierToken(String token) {
        try {
            JwtUtil.generateVerifier().verify(token);
            return  true;
        }catch (JWTVerificationException e){
            log.error(e.getMessage());
            return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean isExpire(String token) {
        try{
            Date date = JwtUtil.generateExpirationDate(token);
            return date.compareTo(new Date()) <= 0? true:false;
        }catch (JWTDecodeException e){
            return false;
        }catch (Exception e){

            return false;
        }

    }

    @Override
    public List<RoleEnum> getRoles(String token) {
        return JwtUtil.generateRole(token);
    }


    @Override
    public JWTVerifier getVerifierWithToken(String token) {
        return JwtUtil.generateVerifier();
    }

    @Override
    public Long getUserIdWithToken(String token) {

       return  JWT.decode(token).getClaim("userId").asLong();

    }

}
