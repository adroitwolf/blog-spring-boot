package run.app.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.app.config.properties.JWTProperties;
import run.app.entity.DTO.User;
import run.app.entity.enums.RoleEnum;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/30 16:25
 * Description: jwt的工具包
 */
@Component
public class JwtUtil {


    @Autowired
    public JWTProperties jwtProperties;

    public static JwtUtil jwtUtil;
//
//
//
//    private JwtUtil() {
//
//    }
//
//    @PostConstruct
//    public static JwtUtil getInstance(){
//        return jwtUtil;
//    }

    @PostConstruct
    public void init() {
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
    }

    public static String generateToken(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        return JWT.create()
                .withIssuer(jwtUtil.jwtProperties.getName())
                .withExpiresAt(new Date(currentTimeMillis + jwtUtil.jwtProperties.getJwtExpires() * 1000))
                .withClaim("userId", user.getId())
                .withArrayClaim("roles", user.getRoles().toArray(new String[user.getRoles().size()]))
                .withAudience(user.getId().toString(), user.getEmail())
                .sign(Algorithm.HMAC256(jwtUtil.jwtProperties.getBase64Secret()));
    }


    public static JWTVerifier generateVerifier() {
        return JWT.require(Algorithm.HMAC256(jwtUtil.jwtProperties.getBase64Secret()))
                .withIssuer(jwtUtil.jwtProperties.getName())
                .build();
    }

    public static Date generateExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }


    public static List<RoleEnum> generateRole(String token) {
        return JWT.decode(token).getClaim("roles").asList(RoleEnum.class);
    }

    public static Long generateExpirationId(String token) {
        return JWT.decode(token).getClaim("userId").asLong();
    }

}
