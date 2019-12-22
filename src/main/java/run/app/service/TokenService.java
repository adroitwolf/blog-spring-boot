package run.app.service;

import com.auth0.jwt.JWTVerifier;
import run.app.entity.DTO.User;
import run.app.entity.enums.RoleEnum;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 16:30
 * Description: 令牌的服务接口
 */
public interface TokenService {

    void authentication(Long id,String token);

    String getToken(User user);

    boolean verifierToken(String token);

    boolean isExpire(String token);

    List<RoleEnum> getRoles(String token);

    JWTVerifier getVerifierWithToken(String token);


    Long getUserIdWithToken(String token);





}
