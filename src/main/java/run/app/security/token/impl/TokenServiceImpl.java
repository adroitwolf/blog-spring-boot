package run.app.security.token.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import run.app.config.properties.JWTProperties;
import run.app.entity.DTO.User;
import run.app.entity.DTO.UserDetail;
import run.app.entity.enums.Role;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerProfile;
import run.app.exception.ServiceException;
import run.app.exception.UnAccessException;
import run.app.exception.UnAuthenticationException;
import run.app.security.token.AuthToken;
import run.app.security.token.TokenService;
import run.app.service.AccountService;
import run.app.service.UserService;
import run.app.util.JwtUtil;
import run.app.util.RedisUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    RedisUtil redisUtil;

    @Autowired
    JWTProperties jwtProperties;



    @Override
    public void authentication(Long id, String token) {
        if(!id.equals(getUserIdWithToken(token))){
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
    public List<Role> getRoles(String token) {
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


    @Override
    public void storage(String token, String username) {
        if(!redisUtil.setWithTime(username,token,12, TimeUnit.HOURS) || !redisUtil.setWithTime(token,username,12,TimeUnit.HOURS)){
            throw new ServiceException("服务异常");
        }
    }

    @Override
    public String generateToken(String username) {

        long time = System.currentTimeMillis();

        String tokenBuff = username + time;

        String token = DigestUtils.md5DigestAsHex(tokenBuff.getBytes());

        return  token;

    }

    @Override
    public AuthToken creatAuthToken(String token) {
        AuthToken authToken = new AuthToken();
        authToken.setAccsess_token(token);
        authToken.setExpire_in(12*60*60);
        return authToken;
    }

    @Override
    public Boolean islogined(String username) {
        return redisUtil.get(username).isPresent();
    }

//    @Override
//    public Boolean isToken(String token) {
//        return redisUtil.get(token).isPresent();
//    }

    @Override
    public UserDetail findUserDetailsByToken(String token)
    {
        Optional<String> username = redisUtil.get(token);

       Long id = accountService.findBloggerIdByUsername(username.get());

       if(id.equals(-1)){
           throw new UnAuthenticationException("用户信息异常！请联系管理员处理");
       }

        @NonNull UserDetail userDetail = userService.findUserDetailByBloggerId(id);

        if(userDetail.equals(null)){
           throw new UnAuthenticationException("用户信息异常！请联系管理员处理");
       }


        return userDetail;
    }




    @Override
    public String getUsernameByToken(String token) {
        return redisUtil.get(token).get();
    }

    @Override
    public boolean logout(String token) {
        if(redisUtil.get(token).isPresent()) {
            String username = redisUtil.get(token).get();
            redisUtil.remove(token);
            redisUtil.remove(username);
            return true;
        }
        log.error("未找到对应{}键值对",token);
        return false;
    }


}
