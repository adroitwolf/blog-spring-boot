package run.app.security.token;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;
import run.app.entity.DTO.UserDetail;
import run.app.entity.model.BloggerAccount;
import run.app.entity.model.BloggerProfile;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 16:30
 * Description: ://TODO ${END}
 */
public interface TokenService {

    String getToken(BloggerAccount bloggerAccount);

    boolean verifierToken(String token);

    boolean isExpire(String token);


    JWTVerifier getVerifierWithToken(String token);


    int getUserIdWithToken(String token);

    void storage(String token,String username);

    String generateToken(String username);

    AuthToken creatAuthToken(String token);


    Boolean islogined(String username);


//    Boolean isToken(String token);

    UserDetail findUserDetailsByToken(String token);


    String getUsernameByToken(String token);


    boolean logout(String token);




}
