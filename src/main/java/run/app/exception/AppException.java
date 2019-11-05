package run.app.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 11:21
 * Description: ://TODO ${END}
 */
public abstract class AppException extends RuntimeException {



    public AppException(String message){
        super(message);
    }

    public AppException(String message, Throwable cause){
        super(message,cause);
    }


    public abstract HttpStatus getStatus();


}
