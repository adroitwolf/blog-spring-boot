package run.app.security.token;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.User;
import run.app.entity.DTO.UserDetail;
import run.app.entity.enums.Role;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerProfile;

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

    List<Role> getRoles(String token);

    JWTVerifier getVerifierWithToken(String token);


    Long getUserIdWithToken(String token);

    void storage(String token,String username);

    String generateToken(String username);

    AuthToken creatAuthToken(String token);


    Boolean islogined(String username);


//    Boolean isToken(String token);

    UserDetail findUserDetailsByToken(String token);


    String getUsernameByToken(String token);


    boolean logout(String token);




}
