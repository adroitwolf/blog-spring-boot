package run.app.security.token;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: WHOAMI
 * Time: 2019 2019/7/22 16:26
 * Description: 认证接口
 */
@Target(ElementType.METHOD)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthTokenInterface {
    boolean isAuthToken() default true;
}
