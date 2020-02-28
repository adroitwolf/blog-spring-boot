package run.app.entity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2020 2020/2/23 8:46
 * Description: 用户认证token
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AutoToken {
    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Refresh token.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;


    /**
     * Expired in. (seconds)
     */
    @JsonProperty("expired_in")
    private long expiredIn;

}
