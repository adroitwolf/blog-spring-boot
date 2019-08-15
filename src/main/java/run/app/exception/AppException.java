package run.app.exception;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 11:21
 * Description: ://TODO ${END}
 */
public abstract class AppException extends RuntimeException {

    private String code;
    private String msg;

    public AppException(String message){
        super(message);
    }

    public AppException(String message, Throwable cause){
        super(message,cause);
    }


}