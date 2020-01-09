package run.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/10/18 15:04
 * Description: 权限异常
 */
public class UnAccessException extends  AppException {
    public UnAccessException(String message) {
        super(message);
    }



    public UnAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
