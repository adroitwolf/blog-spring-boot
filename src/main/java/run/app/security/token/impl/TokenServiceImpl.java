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
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerProfile;
import run.app.exception.ServiceException;
import run.app.exception.UnAuthenticationException;
import run.app.security.token.AuthToken;
import run.app.security.token.TokenService;
import run.app.service.AccountService;
import run.app.service.UserService;
import run.app.util.RedisUtil;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 16:30
 * Description: ://TODO ${END}
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


//    利用Jwt生成token
    @Override
    public String getToken(BloggerAccount bloggerAccount) {

        long currentTimeMillis = System.currentTimeMillis();

        return JWT.create()
                .withIssuer(jwtProperties.getName())
                .withExpiresAt(new Date(currentTimeMillis+jwtProperties.getJwtExpires()))
                .withClaim("userId",bloggerAccount.getId())
                .withAudience(bloggerAccount.getId().toString(),bloggerAccount.getUsername())
                .sign(Algorithm.HMAC256(jwtProperties.getBase64Secret()));
    }

    @Override
    public boolean verifierToken(String token) {

        try {
            JWTVerifier build = JWT.require(Algorithm.HMAC256(jwtProperties.getBase64Secret()))
                    .withIssuer(jwtProperties.getName())
                    .build();

            build.verify(token);
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
            DecodedJWT decode = JWT.decode(token);

            return decode.getExpiresAt().compareTo(new Date()) <= 0? true:false;
        }catch (JWTDecodeException e){
            return false;
        }catch (Exception e){

            return false;
        }

    }

    @Override
    public JWTVerifier getVerifierWithToken(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getBase64Secret())).build();
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

        @NonNull BloggerProfile bloggerProfile = userService.findUserDetailByBloggerId(id);

        if(bloggerProfile.equals(null)){
           throw new UnAuthenticationException("用户信息异常！请联系管理员处理");
       }

        UserDetail userDetail = new UserDetail();
       userDetail.setAvatarId(bloggerProfile.getAvatarId());
       userDetail.setUsername(bloggerProfile.getNickname());
        userDetail.setAboutMe(bloggerProfile.getAboutMe());
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
