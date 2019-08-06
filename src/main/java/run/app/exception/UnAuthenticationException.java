package run.app.exception;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/23 19:06
 * Description: ://TODO ${END}
 */
public class UnAuthenticationException extends AppException {

    public UnAuthenticationException(String message) {
        super(message);
    }

    public UnAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
