package run.app.security.token.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerAccountExample;
import run.app.entity.model.BloggerProfile;
import run.app.entity.model.BloggerProfileWithBLOBs;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.exception.UnAuthenticationException;
import run.app.security.token.AuthToken;
import run.app.security.token.TokenService;
import run.app.service.UserService;
import run.app.util.AppUtil;
import run.app.util.RedisUtil;

import java.util.Optional;
import java.util.UUID;
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
    RedisUtil redisUtil;

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

    @Override
    public Boolean isToken(String token) {
        return redisUtil.get(token).isPresent();
    }

    @Override
    public UserDetail findUserDetailsByToken(String token) {
        Optional<String> username = redisUtil.get(token);

       Integer id = userService.findBloggerIdByUsername(username.get());

       if(id.equals(-1)){
           throw new UnAuthenticationException("用户信息异常！请联系管理员处理");
       }

        @NonNull BloggerProfileWithBLOBs bloggerProfile = userService.findUserDetailByBloggerId(id);

        if(bloggerProfile.equals(null)){
           throw new UnAuthenticationException("用户信息异常！请联系管理员处理");
       }

        UserDetail userDetail = new UserDetail();
       userDetail.setAvatarId(bloggerProfile.getAvatarId());
       userDetail.setUsername(bloggerProfile.getIntro());
       userDetail.setEmail(bloggerProfile.getEmail());
       userDetail.setPhone(bloggerProfile.getPhone());
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
