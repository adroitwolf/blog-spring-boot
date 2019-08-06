package run.app.controller.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.app.entity.DTO.BaseResponse;
import run.app.exception.BadRequestException;
import run.app.exception.ServiceException;
import run.app.exception.UnAuthenticationException;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 11:17
 * Description: ://TODO ${END}
 */
@RestControllerAdvice
@Slf4j
public class HandlerExceptionController {


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handlerBadException(BadRequestException e){
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        baseResponse.setMessage(e.getMessage());

        return baseResponse;

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        log.debug(e.getBindingResult().getAllErrors().toString());

        baseResponse.setMessage("字段验证错误，请重试！");

        return  baseResponse;
    }



    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse handlerUnAuthenticationException(UnAuthenticationException e){
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        log.debug(e.getMessage());

        baseResponse.setMessage(e.getMessage());

        return  baseResponse;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handlerServiceException(ServiceException e){
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        log.debug(e.getMessage());

        baseResponse.setMessage(e.getMessage());

        return  baseResponse;
    }


}
