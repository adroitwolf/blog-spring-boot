package run.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 19:06
 * Description: 服务器拒绝请求 401
 */
public class UnAuthenticationException extends AppException {

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
